package com.base.atlas.oauth2;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@Configuration
public class ResourceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ResourceApplication.class, args);
  }


  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//    jwtAccessTokenConverter.setSigningKey("hLLUARPGQ3dgBcCFKGJJFwBbwkHhNAU3");

    /**
     * 使用rsa非对称加密解密
     */
    Resource resource = new ClassPathResource("public.txt");
    String publicKey = null;
    try {
      publicKey = CharStreams.toString(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    jwtAccessTokenConverter.setVerifierKey(publicKey);

    return jwtAccessTokenConverter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }
}
