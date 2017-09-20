package com.base.atlas.oauth2.util;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2Authorization;
import com.github.scribejava.core.model.OAuthAsyncRequestCallback;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import com.github.scribejava.core.services.Base64Encoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * OAuth请求封装service
 *
 * @author renfei
 *
 *
 */
public class AtlasOAuth20Service extends OAuth20Service {

  /**
   * Default constructor
   *
   * @param api OAuth2.0 api information
   * @param config OAuth 2.0 configuration param object
   */
  public AtlasOAuth20Service(DefaultApi20 api, OAuthConfig config) {
    super(api, config);
  }


  @Override
  protected OAuthRequest createAccessTokenRequest(String code) {
    final OAuthRequest request = new OAuthRequest(getApi().getAccessTokenVerb(), getApi().getAccessTokenEndpoint());
    final OAuthConfig config = getConfig();
    request.addParameter(OAuthConstants.CODE, code);
    request.addParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
    final String scope = config.getScope();
    if (scope != null) {
      request.addParameter(OAuthConstants.SCOPE, scope);
    }

    final String apiKey = config.getApiKey();
    final String apiSecret = config.getApiSecret();
    if (apiKey != null && apiSecret != null) {
      request.addHeader(OAuthConstants.HEADER,
          OAuthConstants.BASIC + ' '
              + Base64Encoder.getInstance()
              .encode(String.format("%s:%s", apiKey, apiSecret).getBytes(Charset.forName("UTF-8"))));
    }

    request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
    return request;
  }
}
