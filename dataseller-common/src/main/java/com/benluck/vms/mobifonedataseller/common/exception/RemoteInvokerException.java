package com.benluck.vms.mobifonedataseller.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 11/14/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoteInvokerException extends Exception{
    public RemoteInvokerException(String message) {
        super(message);
    }
    public RemoteInvokerException(String message, Throwable e) {
        super(message, e);
    }
}
