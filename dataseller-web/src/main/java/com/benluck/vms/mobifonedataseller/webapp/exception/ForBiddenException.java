package com.benluck.vms.mobifonedataseller.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/13/16
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForBiddenException extends RuntimeException{

}
