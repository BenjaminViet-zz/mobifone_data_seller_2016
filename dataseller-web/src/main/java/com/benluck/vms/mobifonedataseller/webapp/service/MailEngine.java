package com.benluck.vms.mobifonedataseller.webapp.service;


import com.benluck.vms.mobifonedataseller.util.MailUtil;
import com.benluck.vms.mobifonedataseller.util.MyMimeMessagePreparator;
import com.benluck.vms.mobifonedataseller.webapp.exception.SendEmailException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * Class for sending e-mail messages based on Velocity templates
 * or with attachments.
 * 
 */
public class MailEngine {
    private final Logger log = Logger.getLogger(getClass());
    private VelocityEngine velocityEngine;
    private String defaultFrom;
    private MailSender emailSender;
    
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setFrom(String from) {
        this.defaultFrom = from;
    }

	public void setEmailSender(MailSender emailSender) {
		this.emailSender = emailSender;
	}

	/**
     * Convenience method for sending messages with attachments.
     * 
     * @param recipients array of e-mail addresses
     * @param sender e-mail address of sender
     * @param resource attachment from classpath
     * @param subject subject of e-mail
     * @param attachmentName name for attachment
     * @throws javax.mail.MessagingException thrown when can't communicate with SMTP server
     */
    public void sendMessage(final String[] recipients, final String sender, final String subject, 
    		final String templateName, final Map model, 
            final ClassPathResource resource, final String attachmentName)
    throws SendEmailException {
    	
        String text = "";
        try {        	
	          text = VelocityEngineUtils.mergeTemplateIntoString(
                      velocityEngine, templateName, "UTF-8", model);
        } catch (Exception e) {
      	  log.error(e.getMessage(), e);
        }
		MyMimeMessagePreparator preparator = new MyMimeMessagePreparator(sender, subject, defaultFrom, text);
		preparator.setRecipients(recipients);
		try {
			doSendMail(preparator);
		}catch (MailException e) {
			throw new SendEmailException(e.getMessage(), e, text);
		}
    }
    
    //send message with ccrecipients and bccrecipients 
    public void sendMessage(final String[] recipients, final String[] ccRecipients, final String[] bccRecipients, final String sender, final String subject, 
    		final String templateName, final Map model, 
            final ClassPathResource resource, final String attachmentName)
    throws SendEmailException {
    	
        String text = "";
        try {        	
	          text = VelocityEngineUtils.mergeTemplateIntoString(
                      velocityEngine, templateName, "UTF-8", model);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
		MyMimeMessagePreparator preparator = new MyMimeMessagePreparator(sender, subject, defaultFrom, text);
		preparator.setRecipients(recipients);
		preparator.setBccRecipients(bccRecipients);
		preparator.setCcRecipients(ccRecipients);
		try {
			doSendMail(preparator);
		}catch (MailException e) {
			throw new SendEmailException(e.getMessage(), e, text);
		}
    }
    public void sendMessage(final String[] recipients, final String[] bccRecipients, final String sender, final String subject, String content) throws MailException {
		MyMimeMessagePreparator preparator = new MyMimeMessagePreparator(sender, subject, defaultFrom, content);
		preparator.setRecipients(recipients);
		preparator.setBccRecipients(bccRecipients);
		doSendMail(preparator);
    }
    
    /**
     * Convenience method for sending messages in BCC with attachments.
     * 
     * @param bccRecipients array of e-mail addresses
     * @param sender e-mail address of sender
     * @param subject subject of e-mail
     * @throws javax.mail.MessagingException thrown when can't communicate with SMTP server
     */
    public void sendMessageBCC(final String[] bccRecipients, final String sender, final String subject, 
    		final String templateName, final Map model) 
    throws MessagingException, MailException {
          String text = "";
          try {
	          text = VelocityEngineUtils.mergeTemplateIntoString(
                      velocityEngine, templateName, "UTF-8", model);
          } catch (Exception e) {
        	  log.error(e.getMessage(), e);
          }
        MyMimeMessagePreparator preparator = new MyMimeMessagePreparator(sender, subject, defaultFrom, text);
        preparator.setBccRecipients(bccRecipients);
    	doSendMail(preparator);
    }
    private void doSendMail(MyMimeMessagePreparator myPre) throws MailException {
		JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) emailSender;
		String defaultFrom = MailUtil.getDefaultFrom();
		if(StringUtils.isNotBlank(myPre.getSender())) {
			defaultFrom = myPre.getSender();				
		} else if(StringUtils.isNotBlank(defaultFrom)) {
			myPre.setSender(defaultFrom);
		}
		javaMailSender.send(myPre);
    }
}
