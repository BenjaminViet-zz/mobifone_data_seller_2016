/**
 * Cache utility - Singleton to monitor the GerneralCacheAdministrator from Infinispan Cache to provide the APIs for caching objects
 */
package com.benluck.vms.mobifonedataseller.common.utils;

import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

/**
 * @author benluck
 *
 */
public class CacheUtil {
    private transient final Logger logger = Logger.getLogger(CacheUtil.class);
    private static CacheUtil instance;

    private Cache<String, Object> cache;
    private int time = 1800;

    private CacheUtil() {
        try {
            InitialContext ic = new InitialContext();
            CacheContainer container = (CacheContainer) ic.lookup("java:jboss/infinispan/container/mobifoneDataSeller");
            this.cache = container.getCache();
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static CacheUtil getInstance() {
        if (instance == null) {
            instance = new CacheUtil();
        }
        return instance;
    }

    public void clearCache() {
        this.cache.clear();
    }

    public Object getValue(String key) {
        return cache.get(key);
    }
    /**
     *
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        this.putValue(key, value, time);
    }

    public void remove(String key) {
        cache.remove(key);
    }
    /**
     * Put object in cache with specified expiration time in second
     * @param key
     * @param value
     * @param expiredTime - Expiration time in second
     */
    public void putValue(String key, Object value, int expiredTime) {
        cache.put(key, value, expiredTime, TimeUnit.SECONDS);
    }
}
