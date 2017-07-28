package com.atlas.business;

import com.atlas.business.dto.User;
import com.atlas.framework.common.JsonSerializer;
import com.atlas.framework.common.redis.MessagePackSerializer;
import com.atlas.framework.web.session.UserSessionManager;
import com.google.common.collect.Lists;
import org.apache.commons.lang.time.DateUtils;
import org.msgpack.MessagePack;
import org.msgpack.template.StringTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * cachemanager 处理
 * Created by renfei on 17/5/11.
 */
@Component
@EnableCaching
public class CachingConfig {

    @Autowired
    JedisConnectionFactory factory;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());

        MessagePack mp = new MessagePack();
        template.setValueSerializer(new MessagePackSerializer<>(mp));
        return template;
    }


    @Bean
    public CacheManager cacheManager() {
        CompositeCacheManager cacheManager = new CompositeCacheManager();

        List<CacheManager> list = Lists.newArrayList();

        RedisCacheManager redisCacheManager = new RedisCacheManager(stringRedisTemplate);
        redisCacheManager.setDefaultExpiration(DateUtils.MILLIS_PER_MINUTE * 30 / DateUtils.MILLIS_PER_SECOND);

        list.add(redisCacheManager);

        cacheManager.setCacheManagers(list);

        return cacheManager;
    }


    @Bean
    public UserSessionManager<User> userSessionManager(){
        JsonSerializer<User> serializer = new JsonSerializer();


        return new UserSessionManager("userSession", cacheManager(), serializer) {
            @Override
            public String generateSID() {
                return UUID.randomUUID().toString().replace("-", "");
            }


        };
    }
}
