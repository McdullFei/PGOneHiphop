package com.base.atlas.oauth2.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Y用于spring security验证用户的provider
 *
 * @author renfei
 */
@Component
public class BusinessUserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(BusinessUserAuthenticationProvider.class);

  @Autowired
  private BusinessUserDetailsService businessUserDetailsService;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
    LOGGER.info("> additionalAuthenticationChecks");

    if (token.getCredentials() == null || userDetails.getPassword() == null) {
      throw new BadCredentialsException("Credentials may not be null.");
    }

    // TODO 用户公共加密方法
    if (!Objects.equals(userDetails.getPassword(), token.getCredentials()/*加密*/)) {
      throw new BadCredentialsException("Invalid credentials.");
    }

    LOGGER.debug("< additionalAuthenticationChecks");
  }

  @Override
  protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    return businessUserDetailsService.loadUserByUsername(username);
  }
}
