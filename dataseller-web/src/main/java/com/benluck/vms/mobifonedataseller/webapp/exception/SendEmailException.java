package com.benluck.vms.mobifonedataseller.webapp.exception;


import javax.mail.MessagingException;

/**
 *  <p>This exception hierarchy aims to let user code find and handle the duplicate object
 * when save or update object into the database</p>
 * 
 * @author benluck
 *
 */
public class SendEmailException extends MessagingException {
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 */
	private String emailContent;
	
	/**
	 * Constructor for DuplicateException.
	 * @param msg the detail message
	 */
	public SendEmailException(final String message) {
		super(message);
	}
	
	/**
	 * Constructor for DuplicateException.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using a underlying
	 * data access API such as JDBC)
	 */
	public SendEmailException(String msg, Exception cause, String emailContent) {
		super(msg, cause);
		this.emailContent = emailContent;
	}
	public SendEmailException(String msg, Exception cause) {
		super(msg, cause);
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
}
