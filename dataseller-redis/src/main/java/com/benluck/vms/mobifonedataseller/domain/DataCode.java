package com.benluck.vms.mobifonedataseller.domain;

import com.benluck.vms.mobifonedataseller.cache.Cachable;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class DataCode implements Cachable{

    private static final long serialVersionUID = -6466983189996530211L;

    public static final String OBJECT_KEY = "MOBI_DATA_DATA_CODE";

    public static final String KEY = "MOBI_DATA_DATA_CODE_KEY";

    public DataCode() {
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }
}
