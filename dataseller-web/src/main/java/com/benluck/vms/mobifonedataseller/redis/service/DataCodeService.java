package com.benluck.vms.mobifonedataseller.redis.service;

import com.benluck.vms.mobifonedataseller.redis.cache.Cachable;
import com.benluck.vms.mobifonedataseller.redis.domain.DataCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
@org.springframework.stereotype.Service
public class DataCodeService implements Service<DataCode> {

    @Autowired
    RedisTemplate<String, Cachable> redisTemplate;

    @Override
    public void put(DataCode dataCode) {
        redisTemplate.opsForHash().put(dataCode.getKey(), dataCode.getHashKey(), dataCode);
    }

    @Override
    public DataCode get(String objectKey, String key) {
        return (DataCode) redisTemplate.opsForHash().get(objectKey, key);
    }
}
