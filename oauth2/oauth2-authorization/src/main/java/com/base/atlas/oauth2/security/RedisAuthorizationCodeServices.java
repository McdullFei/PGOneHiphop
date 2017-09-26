package com.base.atlas.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 授权码修改为分布式存储，并且修改长度为12位
 *
 * @author renfei
 */
@Component
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

  @Autowired
  private RedisTemplate<String, Object> jdkRedisTemplate;

  @Autowired
  private RandomValueStringGenerator generator;

  private static final long TIMEOUT = 30; // 30分钟

  @Override
  protected void store(String code, OAuth2Authentication authentication) {
    jdkRedisTemplate.opsForValue().set(code, authentication);
    jdkRedisTemplate.expire(code, TIMEOUT, TimeUnit.MINUTES);
  }

  @Override
  protected OAuth2Authentication remove(String code) {
    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)jdkRedisTemplate.opsForValue().get(code);
    if(oAuth2Authentication != null){

      jdkRedisTemplate.delete(code);
    }
    return oAuth2Authentication;
  }

  @Override
  public String createAuthorizationCode(OAuth2Authentication authentication) {
    String code = generator.generate();
    this.store(code, authentication);
    return code;
  }
}
