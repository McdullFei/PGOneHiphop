package com.base.atlas.oauth2.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义jwt token内容的关键类
 *
 * @author renfei
 */
@Component
public class BusinessAuthenticationConverter implements UserAuthenticationConverter {
  @Override
  public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
    LinkedHashMap response = new LinkedHashMap();
    response.put("user_name", userAuthentication.getName());
    response.put("atlas_id", ((User) userAuthentication.getPrincipal()).getAtlasId());
    if (userAuthentication.getAuthorities() != null && !userAuthentication.getAuthorities().isEmpty()) {
      response.put("authorities", AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities()));
    }

    return response;
  }

  @Override
  public Authentication extractAuthentication(Map<String, ?> map) {
    return null;
  }
}
