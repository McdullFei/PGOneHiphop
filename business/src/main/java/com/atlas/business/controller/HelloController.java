package com.atlas.business.controller;

import com.atlas.business.dao.UserDao;
import com.atlas.business.dto.User;
import com.atlas.business.service.IUserService;
import com.atlas.business.service.impl.UserService;
import com.atlas.framework.common.http.HttpClientFactory;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by renfei on 17/6/5.
 */
@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private IUserService userService;

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value = "/hello", method= RequestMethod.GET)
    public User index() {
        //User list = userService.login();

        User user = userService.getUserFromSession("73638f8a55dc4f81a8722b6d908ea9a7");

        logger.error("的后端后端好得很的");

        return user;
    }


    @Autowired
    private HttpClientFactory httpClientFactory;

    @ApiOperation(value="获取用户列表", notes="")
    @ApiIgnore
    @RequestMapping("/hello1")
    public String apiIgnore() throws Exception {
        Assert.isNull(httpClientFactory.getObject(), "hhhhh");

        return "dddd";
    }
}
