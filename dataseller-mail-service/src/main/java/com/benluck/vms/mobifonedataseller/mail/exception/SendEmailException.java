package com.benluck.vms.mobifonedataseller.mail.exception;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 12/5/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendEmailException extends Exception{
    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendEmailException(Throwable cause) {
        super(cause);
    }
}
