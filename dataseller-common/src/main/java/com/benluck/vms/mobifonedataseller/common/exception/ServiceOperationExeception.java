package com.benluck.vms.mobifonedataseller.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 11/16/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceOperationExeception extends Exception{
    public ServiceOperationExeception(String message) {
        super(message);
    }
    public ServiceOperationExeception(String message, Throwable e) {
        super(message, e);
    }
    public ServiceOperationExeception(Throwable e) {
        super(e);
    }
}
