package com.base.atlas.oauth2.security;

import com.base.atlas.oauth2.util.ClientSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * oauth2 服务配置
 *
 * @author renfei
 */
@Configuration
public class AuthenticationConfiguration {

  public static final String RESOURCE_ID = "RESOURCE_ID";// 定义resource id主要和资源服务器进行验证的

  /**
   * 授权服务器
   */
  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private BusinessUserDetailsService businessUserDetailsService;

    @Autowired
    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      super.configure(security);
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory()
          .withClient(ClientSecret.MONSTER.getClientId())
          .secret(ClientSecret.MONSTER.getClientSecret())
          .authorizedGrantTypes("authorization_code", "refresh_token")
          .scopes("read", "write").resourceIds(RESOURCE_ID) // client1
          .and()
          .inMemory().withClient(ClientSecret.CAPTAIN.getClientId())
          .secret(ClientSecret.CAPTAIN.getClientSecret())
          .authorizedGrantTypes("authorization_code", "refresh_token")
          .scopes("read", "write").resourceIds(RESOURCE_ID); // client2
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      //AuthenticationManager 用在密码授权模式下
      // userDetailsService 设置在GlobalAuthenticationManagerConfigurer 上面refresh_token 就会检查账号是否有效
      // 授权码模式使用 authorizationCodeServices  重写InMemoryAuthorizationCodeServices实现
      // tokenGranter 作扩展用途的，即标准的四种授权模式已经满足不了你的需求的时候，才会考虑使用这个。

      endpoints.accessTokenConverter(jwtAccessTokenConverter)
          .tokenStore(tokenStore)
          .userDetailsService(businessUserDetailsService)
          .authorizationCodeServices(redisAuthorizationCodeServices);//支持分布式授权码
    }
  }

  /**
   * 认证配置
   * 需要继承用户中心
   *
   *
   * SpringBoot1.5 @EnableResourceServer和@EnableWebSecurity配置的HttpSecurity有先后顺序的问题，需要特殊设置
   *
   * @author renfei
   */
  @Configuration
  @EnableWebSecurity
  public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private BusinessUserDetailsService businessUserDetailsService;

    @Autowired
    private BusinessUserAuthenticationProvider businessUserAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(businessUserDetailsService);// spring security user认证service
      auth.authenticationProvider(businessUserAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // FIXME 由于没有继承session disable csrf
      http.csrf().disable().authorizeRequests()
          .antMatchers("/oauth/authorize").authenticated()
          .and()
          .httpBasic().realmName("OAuth Server");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }
  }

}
