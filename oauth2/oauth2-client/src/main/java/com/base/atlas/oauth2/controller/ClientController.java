package com.base.atlas.oauth2.controller;

import com.alibaba.fastjson.JSONObject;
import com.base.atlas.oauth2.util.ClientSecret;
import com.base.atlas.oauth2.util.AtlasOauthApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 授权码模式的callback，放在请求端
 *
 *
 * FIXME oauth2.0授权服务的session保存client端，这样需要客户端自己实现登陆。
 *        这里也可以使用auth端的登陆，这样需要auth端集成session机制（TODO spring session）
 *
 * @author renfei
 *
 */
@Controller
public class ClientController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

  private static final String CALLBACK_URL = "http://127.0.0.1:20644/callback";// 授权码回调函数

  private static final String RESOURCE_URL = "http://127.0.0.1:20633/api/school/?2"; // 资源服务器受限资源

  // 为防止CSRF跨站攻击，每次请求STATE的值应该不同，可以放入Session！
  // 由于都是localhost测试，所以session无法保持，用一个固定值。
  private static final String STATE = "secret-rensanning";

  @Autowired
  private AtlasOauthApi api;


  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/signin")
  public void signin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    LOGGER.debug("signin");
    LOGGER.info("session id:{}", request.getSession().getId());

    OAuth20Service service = new ServiceBuilder(ClientSecret.MONSTER.getClientId())
        .apiSecret(ClientSecret.MONSTER.getClientSecret())
        .scope("read")
        .state(STATE)
        .callback(CALLBACK_URL)
        .build(api);

    String authorizationUrl = service.getAuthorizationUrl();
    LOGGER.info("redirectURL:{}", authorizationUrl);

    response.sendRedirect(authorizationUrl);
  }

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


  @GetMapping("/profile")
  public String profile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    LOGGER.debug("profile");

    OAuth2AccessToken accessToken = (OAuth2AccessToken) request.getSession().getAttribute("SESSION_KEY_ACCESS_TOKEN");

    OAuth20Service service = new ServiceBuilder(ClientSecret.MONSTER.getClientId())
        .apiSecret(ClientSecret.MONSTER.getClientSecret())
        .scope("read")
        .state(STATE)
        .callback(CALLBACK_URL)
        .build(api);

    getProfile(model, service, accessToken);

    return "profile";
  }

  private void getProfile(Model model, final OAuth20Service service, OAuth2AccessToken accessToken) throws Exception {
    OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, RESOURCE_URL);
    service.signRequest(accessToken, oauthRequest);

    Response resourceResponse = service.execute(oauthRequest);

    LOGGER.info("code:{}", resourceResponse.getCode());
    LOGGER.info("message:{}", resourceResponse.getMessage());
    LOGGER.info("body:{}", resourceResponse.getBody());

    JSONObject obj = JSONObject.parseObject(resourceResponse.getBody());

    LOGGER.info("json:{}", obj.toString());

    model.addAttribute("json",  obj.toString());
  }

}
