package com.base.atlas.oauth2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

/**
 * cachemanager 处理
 * Created by renfei on 17/5/11.
 */
@Configuration
@EnableCaching
public class CachingConfig {
  private static final Logger logger = LoggerFactory.getLogger(CachingConfig.class);

  @Autowired
  JedisConnectionFactory factory;

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  /**
   * 使用java 自带序列化
   *
   * @return
   */
  @Bean(name = "jdkRedisTemplate")
  public RedisTemplate<String, Object> jdkRedisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate();
    template.setConnectionFactory(factory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    return template;
  }


  @Bean
  public RandomValueStringGenerator randomValueStringGenerator() {
    return new RandomValueStringGenerator(16);

  }
}
