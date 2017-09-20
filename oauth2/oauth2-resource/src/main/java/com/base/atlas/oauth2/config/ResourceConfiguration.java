package com.base.atlas.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务器 项目中资源服务器和授权服务器是分离的
 *
 * @author renfei
 */
@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

  public static final String RESOURCE_ID = "RESOURCE_ID";

  @Autowired
  private TokenStore tokenStore;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    // 这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证
    resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // FIXME 这里进行资源保护，只有授权的用户才能访问 ， 由于没有继承session disable csrf
    http.csrf().disable().authorizeRequests().antMatchers("/api/**").authenticated();
//          .antMatchers(HttpMethod.GET, "/a/**").access("#oauth2.hasScope('read')")
//          .antMatchers(HttpMethod.POST, "/a/**").access("#oauth2.hasScope('write')");// 只匹配api开头的受限资源，这里需要枚举各产品url或者不设置
  }

}
