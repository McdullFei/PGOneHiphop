package com.base.atlas.oauth2;

import com.base.atlas.oauth2.security.BusinessAuthenticationConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@SpringBootApplication
@Configuration
public class AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }

  /**
   * 用定制的User token convert
   *
   * @return
   */
  @Bean
  public DefaultAccessTokenConverter tokenConverter(){
    DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    tokenConverter.setUserTokenConverter(new BusinessAuthenticationConverter());

    return tokenConverter;
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//    jwtAccessTokenConverter.setSigningKey("hLLUARPGQ3dgBcCFKGJJFwBbwkHhNAU3");
    // 用定制的User token convert
    jwtAccessTokenConverter.setAccessTokenConverter(tokenConverter());

    //使用非对称加密
    KeyStoreKeyFactory keyStoreKeyFactory =
        new KeyStoreKeyFactory(new ClassPathResource("atlas.jks"), "atlas123".toCharArray());
    jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("atlas"));


    return jwtAccessTokenConverter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

}
