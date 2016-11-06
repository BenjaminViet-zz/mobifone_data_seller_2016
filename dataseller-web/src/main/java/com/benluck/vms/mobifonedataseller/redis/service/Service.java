package com.benluck.vms.mobifonedataseller.redis.service;

import com.benluck.vms.mobifonedataseller.redis.cache.Cachable;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public interface Service <V extends Cachable>{

    public void put(V obj);

    public V get(String objectKey, String key);
}
