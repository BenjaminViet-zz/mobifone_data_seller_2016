package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.context.AppContext;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/16/16
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class RedisUtil {
    private Logger logger = Logger.getLogger(RedisUtil.class);

    private static RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) AppContext.getApplicationContext().getBean("redisTemplate");

    public static void lockOrUnlockRedisKey(String key, String hashKey, boolean isLock){
        redisTemplate.opsForHash().put(key, hashKey, isLock);
    }

    public static boolean getLockRedisKey(String key, String hashKey){
        Object lock = redisTemplate.opsForHash().get(key, hashKey);
        if(lock == null){
            return false;
        }
        return Boolean.valueOf(lock.toString());
    }
}
