package com.benluck.vms.mobifonedataseller.redis.domain;

import com.benluck.vms.mobifonedataseller.redis.cache.Cachable;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class DataCode implements Cachable {

    private static final long serialVersionUID = -6466983189996530211L;

    private String keyByYear;
    private String hasKeyByUnitPrice;
    private HashSet<String> dataCodeHashSet = new HashSet<String>();

    public String getKeyByYear() {
        return keyByYear;
    }

    public void setKeyByYear(String keyByYear) {
        this.keyByYear = keyByYear;
    }

    public String getHasKeyByUnitPrice() {
        return hasKeyByUnitPrice;
    }

    public void setHasKeyByUnitPrice(String hasKeyByUnitPrice) {
        this.hasKeyByUnitPrice = hasKeyByUnitPrice;
    }

    public DataCode() {
    }

    public DataCode(String keyByYear, String hasKeyByUnitPrice) {
        this.keyByYear = keyByYear;
        this.hasKeyByUnitPrice = hasKeyByUnitPrice;
    }

    @Override
    public String getKey() {
        return getKeyByYear();
    }

    @Override
    public String getHashKey() {
        return getHasKeyByUnitPrice();
    }

    public HashSet<String> getDataCodeHashSet() {
        return dataCodeHashSet;
    }

    public void setDataCodeHashSet(HashSet<String> dataCodeHashSet) {
        this.dataCodeHashSet = dataCodeHashSet;
    }
}
