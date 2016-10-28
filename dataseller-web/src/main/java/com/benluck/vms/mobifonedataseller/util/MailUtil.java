package com.benluck.vms.mobifonedataseller.util;


import com.benluck.vms.mobifonedataseller.utils.WebConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
	/**
	 * load email config for emailsender 
	 * @param mailSender
	 * @return defaultFrom
	 */
	 public static String loadWebConfig(MailSender mailSender) {
	    	JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) mailSender;
	    	String host, username, password, defaultFrom;
	    	host = username = password  = defaultFrom = null;
	    	Integer port = null;
	    	Boolean isSsl = Boolean.FALSE;
	    	host = WebConfig.getInstance().getProperty("mail.host");
			try {
				port = Integer.valueOf(WebConfig.getInstance().getProperty("mail.port"));
			}catch (NumberFormatException e) {
				//ignore
			}
			isSsl = Boolean.valueOf(WebConfig.getInstance().getProperty("mail.smtp.ssl.enable"));
	    	if(StringUtils.isBlank(defaultFrom)) {
	    		defaultFrom = WebConfig.getInstance().getProperty("mail.default.from");
	    	}
	    	if(StringUtils.isNotBlank(host)) {
	    		javaMailSender.setHost(host);
	    	}
	    	if(port != null) {
	    		javaMailSender.setPort(port);
	    	}
	    	if(StringUtils.isNotBlank(username)) {
	    		javaMailSender.setUsername(username);
	    	}
	    	if(StringUtils.isNotBlank(password)) {
	    		javaMailSender.setPassword(password);
	    	}
	    	if(!isSsl) {
	    		javaMailSender.getJavaMailProperties().remove("mail.smtp.socketFactory.class");
	    	}
	    	return defaultFrom;
	 }
	 public static String getDefaultFrom() {
		return WebConfig.getInstance().getProperty("mail.default.from"); 
	 }
}
