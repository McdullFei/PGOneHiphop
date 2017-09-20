package com.base.atlas.oauth2.security;

import com.base.atlas.oauth2.repository.UserRepository;
import com.base.atlas.oauth2.util.AccountRole;
import com.google.common.collect.Lists;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * B端用户认证service
 *
 * @author renfei
 */
@Service
@Order(100)
public class BusinessUserDetailsService implements UserDetailsService {

  @Resource
  private UserRepository userRepository;

  /**
   * 通过登录名进行认证
   * @param loginName login_name
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
    com.base.atlas.oauth2.dto.User user = userRepository.findUserByUsername(loginName);

    if (user != null) {
      Collection<GrantedAuthority> authorityCollection = Lists.newArrayList();
      authorityCollection.add(new SimpleGrantedAuthority(AccountRole.ROLE_USER.name()));// ROLE_USER、ROLE_ADMIN、ROLE_SYSADMIN

      User userDetails = new User(user.getUsername()
          , user.getPassword()
          , Boolean.TRUE
          , true
          , true
          , true
          , authorityCollection);

      return userDetails;
    } else {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
    }
  }
}
