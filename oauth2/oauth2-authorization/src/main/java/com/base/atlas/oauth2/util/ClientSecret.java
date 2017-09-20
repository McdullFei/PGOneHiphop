package com.base.atlas.oauth2.util;

/**
 * 接入的各个产品所授权的secret
 *
 * @author renfei
 *
 */
public enum ClientSecret {
  MONSTER("monster", "TFUu7znXJUVJe9"), CAPTAIN("captain", "TbPPpWzk3z[TJ8");
  ClientSecret(String clientId, String clientSecret){
    this.clientId = clientId;
    this.clientSecret = clientSecret;

  }
  private String clientId;
  private String clientSecret;

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }
}
