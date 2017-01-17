package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/16/16
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class RedisUtil {
    private static Logger logger = Logger.getLogger(RedisUtil.class);

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

    private static void updateRedisValueByKey(String key, String hashKey, Object value){
        if(Config.getInstance().getProperty("redis.turn_on").equals("true")){
            redisTemplate.opsForHash().put(key, hashKey, value);
        }
    }

    public static Object getRedisValueByKey(String key, String hashKey){
        if(Config.getInstance().getProperty("redis.turn_on").equals("true")){
            return (Object) redisTemplate.opsForHash().get(key, hashKey);
        }else{
            if(key.equals(hashKey) && key.equals(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY)){
                return true;
            }
            return null;
        }
    }

    public static HashSet<String> getUsedCardCodeByKey(){
        HashSet<String> usedCardCodeHS = null;
        if(Config.getInstance().getProperty("redis.turn_on").equals("true")){
            usedCardCodeHS = (HashSet<String>)redisTemplate.opsForHash().get(Constants.KEY_USED_2016, Constants.HAS_KEY_USED_2016UNIT_PRICE_10);
            if(usedCardCodeHS == null){
                return new HashSet<String>();
            }else{
                return usedCardCodeHS;
            }
        }else{
            return new HashSet<String>();
        }
    }

    public static void updateUsedCardCodeByKey(HashSet<String> newUpdatedCardCodeHS){
        if(newUpdatedCardCodeHS == null || newUpdatedCardCodeHS.size() == 0){
            return;
        }
        if(Config.getInstance().getProperty("redis.turn_on").equals("true")){
            redisTemplate.opsForHash().put(Constants.KEY_USED_2016, Constants.HAS_KEY_USED_2016UNIT_PRICE_10, newUpdatedCardCodeHS);

            // update flag for Imported Used Card Code list already.
            updateRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, true);
        }
    }

    public static boolean pingRedisServer(){
        logger.info("Pinging to Redis Server...");
        try{
            String redisResponse = redisTemplate.getConnectionFactory().getConnection().ping();
            logger.info("Redis response: " + redisResponse);
            if(redisResponse.equals("PONG")){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            logger.info("Redis is not responsed");
            return false;
        }
    }
}
