package com.base.atlas.oauth2.util;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Component;

/**
 * 客户端调用授权服务的api
 *
 *
 * @author renfei
 */
@Component
public class AtlasOauthApi extends DefaultApi20 {

  @Override
  public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
    return OAuth2AccessTokenJsonExtractor.instance();
  }

  @Override
  public OAuth20Service createService(OAuthConfig config) {
    return new AtlasOAuth20Service(this, config);
  }


  @Override
  public String getAccessTokenEndpoint() {
    return "http://127.0.0.1:20622/oauth2/token";
  }

  @Override
  protected String getAuthorizationBaseUrl() {
    return "http://127.0.0.1:20622/oauth2/authorize";
  }
}
