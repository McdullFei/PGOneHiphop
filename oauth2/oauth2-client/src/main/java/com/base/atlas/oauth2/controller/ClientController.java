package com.base.atlas.oauth2.controller;

import com.base.atlas.oauth2.util.ClientSecret;
import com.base.atlas.oauth2.util.AtlasOauthApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 授权码模式的callback，放在请求端
 *
 * @author renfei
 *
 */
@Controller
public class ClientController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

  private static final String CALLBACK_URL = "http://127.0.0.1:20644/callback";

  // 为防止CSRF跨站攻击，每次请求STATE的值应该不同，可以放入Session！
  // 由于都是localhost测试，所以session无法保持，用一个固定值。
  private static final String STATE = "secret-rensanning";

  @Autowired
  private AtlasOauthApi api;

  @GetMapping("/callback")
  @ResponseBody
  public OAuth2AccessToken callback(@RequestParam(value = "code", required = false) String code,
                                    @RequestParam(value = "state", required = false) String state,
                                    HttpServletRequest request, HttpServletResponse response
  ) throws Exception {
    LOGGER.info("callback");
    LOGGER.info("code:{} state:{}", code, state);
    LOGGER.info("session id:{}", request.getSession().getId());

    if (STATE.equals(state)) {
      LOGGER.info("State OK!");
    } else {
      LOGGER.error("State NG!");
    }

    OAuth20Service service = new ServiceBuilder(ClientSecret.MONSTER.getClientId())
        .apiSecret(ClientSecret.MONSTER.getClientSecret())
        .scope("read")
        .state(STATE)
        .callback(CALLBACK_URL)
        .build(api);

    OAuth2AccessToken accessToken = service.getAccessToken(code);
    request.getSession().setAttribute("SESSION_KEY_ACCESS_TOKEN", accessToken);

    return accessToken;
  }

}
