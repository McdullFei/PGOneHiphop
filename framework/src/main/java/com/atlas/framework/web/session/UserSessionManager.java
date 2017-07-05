package com.atlas.framework.web.session;

import com.atlas.framework.common.Serializer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

/**
 * session缓存管理,通过spring cachemanager来支持多缓存
 * 缓存失效时间在实现CacheManager时设置
 *
 * FIXME 因为有自己实现的序列化和反序列化方法,所以暂不支持cacheable注解,如果需要支持注解需要继承cachemanager而不是引用
 * 而且一般获取session都是在拦截器做不需要注解支持
 *
 *
 * Created by renfei on 17/6/21.
 */
public abstract class UserSessionManager<T> {

    private CacheManager cacheManager;

    private Serializer<T> serializer;

    private String name;

    protected UserSessionManager(String cacheName, CacheManager cacheManager, Serializer serializer){
        this.cacheManager = cacheManager;
        this.serializer = serializer;
        this.name = cacheName;
    }


    /**
     * sessionID生成器,客户端可以自己实现
     * @return
     */
    public abstract String generateSID();

    /**
     * 存放session
     * @param object
     */
    public void set(T object){

        String data = serializer.serialize(object);

        Cache cache = cacheManager.getCache(this.name);
        cache.put(generateSID(), data);
    }

    /**
     * 获取session 存放数据
     * @param SID
     * @param clazz
     * @return
     */
    public T get(String SID, Class<T> clazz){
        Cache cache = cacheManager.getCache(this.name);

        String value = cache.get(SID, String.class);

        Assert.notNull(value, "get session is failure.");

        return serializer.deserialize(value, clazz);
    }

    /**
     * 清除user session
     * @param SID
     */
    public void clear(String SID){
        Cache cache = cacheManager.getCache(this.name);
        cache.evict(SID);
    }

}
