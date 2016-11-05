package com.benluck.vms.mobifonedataseller.cache;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public interface Cachable extends Serializable{
    public String getKey();

    public String getObjectKey();
}
