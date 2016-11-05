package com.benluck.vms.mobifonedataseller.service;

import com.benluck.vms.mobifonedataseller.cache.Cachable;
import com.benluck.vms.mobifonedataseller.domain.DataCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DataCodeService implements com.benluck.vms.mobifonedataseller.service.Service<DataCode>{

    private String KEY = "MOBI_DATA_DATA_CODE";

//    @Autowired
    RedisTemplate<String, Cachable> redisTemplate;

    @Override
    public void put(DataCode dataCode) {
        redisTemplate.opsForHash().put(dataCode.getObjectKey(), dataCode.getKey(), dataCode);
    }

    @Override
    public DataCode get() {
        return (DataCode) redisTemplate.opsForHash().get(DataCode.OBJECT_KEY, DataCode.KEY);
    }
}
