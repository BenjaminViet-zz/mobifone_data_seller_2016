package com.benluck.vms.mobifonedataseller.mail.impl;

import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.mail.EmailLocalBean;
import com.benluck.vms.mobifonedataseller.mail.exception.MailSendException;
import com.benluck.vms.mobifonedataseller.mail.exception.SendEmailException;
import com.benluck.vms.mobifonedataseller.mail.utils.SmartMimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.activation.FileTypeMap;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hieu
 * Date: 12/5/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "EmailSessionBeanEJB")
public class EmailSessionBean implements EmailLocalBean {
    public static final String DEFAULT_PROTOCOL = "smtp";
    public static final Integer DEFAULT_PORT = 25;
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String CONTENT_TYPE_HTML = "text/html";
    private static final String HEADER_MESSAGE_ID = "Message-ID";
    private static Logger logger = Logger.getLogger(EmailSessionBean.class);
    private String defaultSender;
    String userName, password, protocol, host;
    Integer port;
    boolean auth, ssl, starttlsEnable;
    private Session session;

    @PostConstruct
    void init() {
        defaultSender =  Config.getInstance().getProperty("mail.default.from");
        userName = Config.getInstance().getProperty("mail.username");
        password = Config.getInstance().getProperty("mail.password");
        protocol = Config.getInstance().getProperty("mail.transport.protocol");
        if(StringUtils.isBlank(protocol)){
            protocol = DEFAULT_PROTOCOL;
        }
        host = Config.getInstance().getProperty("mail.host");
        String sPort = Config.getInstance().getProperty("mail.port");
        port = DEFAULT_PORT;
        if(StringUtils.isNotBlank(sPort)) {
            try{
                port = Integer.valueOf(sPort);
            }catch (NumberFormatException e){
                logger.warn(e.getMessage());
            }
        }
        auth = Boolean.valueOf(Config.getInstance().getProperty("mail.smtp.auth"));
        ssl = Boolean.valueOf(Config.getInstance().getProperty("mail.smtp.ssl.enable"));
        starttlsEnable = Boolean.valueOf(Config.getInstance().getProperty("mail.smtp.starttls.enable"));
        Properties p = new Properties();
        p.put("runtime.log.logsystem.class","org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        p.put("runtime.log.logsystem.log4j.category","org.apache.velocity");
        p.put("resource.loader","class");
        p.put("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);

        logger.info("defaultSender = " + defaultSender);
    }

    @Override
    public void sendMessage(List<String> toAddresses, List<String> ccAddresses, List<String> bccAddresses, String subject, String templateName, Map<String, Object> model) {
        String content = getContent(templateName, model);
        sendMessage(toAddresses, ccAddresses, bccAddresses, null, subject, content);
    }

    @Override
    public void sendMessage(List<String> toAddresses, List<String> ccAddresses, List<String> bccAddresses, String sender, String subject, String templateName, Map<String, Object> model) {
        String content = getContent(templateName, model);
        sendMessage(toAddresses, ccAddresses, bccAddresses, sender, subject, content);
    }

    @Override
    public void sendMessage(List<String> toAddresses, List<String> ccAddresses, List<String> bccAddresses, String sender, String subject, String content) {
        if (sender == null) {
            sender = defaultSender;
        }
        try{
            MimeMessage mimeMessage = createMimeMessage();
            mimeMessage.setFrom(parseAddress(sender));
            mimeMessage.setSubject(subject, DEFAULT_ENCODING);
            mimeMessage.setContent(content, CONTENT_TYPE_HTML + ";charset=" + DEFAULT_ENCODING);
            if (toAddresses != null && toAddresses.size() > 0) {
                InternetAddress[] addresses = new InternetAddress[toAddresses.size()];
                for (int i = 0; i < toAddresses.size(); i++) {
                    addresses[i] = parseAddress(toAddresses.get(i));
                }
                mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
            }
            if (ccAddresses != null && ccAddresses.size() > 0) {
                InternetAddress[] addresses = new InternetAddress[toAddresses.size()];
                for (int i = 0; i < toAddresses.size(); i++) {
                    addresses[i] = parseAddress(toAddresses.get(i));
                }
                mimeMessage.setRecipients(Message.RecipientType.CC, addresses);
            }
            if (bccAddresses != null && bccAddresses.size() > 0) {
                InternetAddress[] addresses = new InternetAddress[toAddresses.size()];
                for (int i = 0; i < toAddresses.size(); i++) {
                    addresses[i] = parseAddress(toAddresses.get(i));
                }
                mimeMessage.setRecipients(Message.RecipientType.BCC, addresses);
            }
            List<MimeMessage> mimeMessages = new ArrayList<MimeMessage>();
            mimeMessages.add(mimeMessage);
            send(mimeMessages.toArray(new MimeMessage[mimeMessages.size()]));
            logger.info("Email sent from " + sender);

        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private InternetAddress parseAddress(String address) throws MessagingException {
        InternetAddress[] parsed = InternetAddress.parse(address);
        if (parsed.length != 1) {
            throw new AddressException("Illegal address", address);
        }
        InternetAddress raw = parsed[0];
        return raw;
    }

    public MimeMessage createMimeMessage() {
        return new SmartMimeMessage(getSession(), getDefaultEncoding(), getDefaultFileTypeMap());
    }
    private void send(MimeMessage[] mimeMessages) throws AuthenticationFailedException, MailSendException {
        doSend(mimeMessages, null);
    }
    private FileTypeMap getDefaultFileTypeMap() {
        return null;
    }
    private void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws AuthenticationFailedException, MailSendException {
        Map<Object, Exception> failedMessages = new LinkedHashMap<Object, Exception>();

        Transport transport;
        try {
            transport = getTransport(getSession());
            transport.connect(host, port, userName, password);
        }
        catch (AuthenticationFailedException ex) {
            throw ex;
        }
        catch (MessagingException ex) {
            // Effectively, all messages failed...
            for (int i = 0; i < mimeMessages.length; i++) {
                Object original = (originalMessages != null ? originalMessages[i] : mimeMessages[i]);
                failedMessages.put(original, ex);
            }
            throw new MailSendException("Mail server connection failed", ex, failedMessages);
        }

        try {
            for (int i = 0; i < mimeMessages.length; i++) {
                MimeMessage mimeMessage = mimeMessages[i];
                try {
                    if (mimeMessage.getSentDate() == null) {
                        mimeMessage.setSentDate(new Date());
                    }
                    String messageId = mimeMessage.getMessageID();
                    mimeMessage.saveChanges();
                    if (messageId != null) {
                        // Preserve explicitly specified message id...
                        mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
                    }
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                }
                catch (MessagingException ex) {
                    Object original = (originalMessages != null ? originalMessages[i] : mimeMessage);
                    failedMessages.put(original, ex);
                }
            }
        }
        finally {
            try {
                transport.close();
            }
            catch (MessagingException ex) {
                if (!failedMessages.isEmpty()) {
                    throw new MailSendException("Failed to close server connection after message failures", ex,
                            failedMessages);
                }
                else {
                    throw new MailSendException("Failed to close server connection after message sending", ex);
                }
            }
        }

        if (!failedMessages.isEmpty()) {
            throw new MailSendException(failedMessages);
        }
    }

    private Transport getTransport(Session session) throws NoSuchProviderException {
        return session.getTransport(protocol);
    }
    private synchronized Session getSession() {
        if (this.session == null) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.ssl.enable", ssl);
            props.put("mail.smtp.starttls.enable", starttlsEnable);
            this.session = Session.getInstance(props);
        }
        return this.session;
    }
    private String getDefaultEncoding() {
        return DEFAULT_ENCODING;
    }
    private String getContent(String templateName, Map<String, Object> model) {
        VelocityContext context = new VelocityContext();
        if(model != null) {
            Set<String> keys = model.keySet();
            for (String key: keys) {
                context.put(key, model.get(key));
            }
        }
        Template template =  Velocity.getTemplate(templateName, "UTF-8");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
