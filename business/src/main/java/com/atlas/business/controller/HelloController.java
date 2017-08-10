package com.atlas.business.controller;

import com.atlas.business.dto.User;
import com.atlas.business.service.IUserService;
import com.atlas.core.web.AcResult;
import com.atlas.framework.common.http.HttpClientFactory;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;

/**
 * Created by renfei on 17/6/5.
 */
@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private IUserService userService;

    @ApiOperation(value="测试session", notes="")
    @RequestMapping(value = "/hello", method= RequestMethod.GET)
    @ResponseBody public AcResult index() {

        User user = userService.getUserFromSession("73638f8a55dc4f81a8722b6d908ea9a7");

        logger.error("的后端后端好得很的");

        return AcResult.ok(user);
    }


    @ApiOperation(value="根据uid获取用户", notes="")
    @RequestMapping(value = "/user/{uid}", method= RequestMethod.GET)
    @ResponseBody public AcResult getUser(@PathVariable("uid") Long uid) {
        User user = userService.getUserById(uid);

        if (Objects.isNull(user)){
            return AcResult.error();
        }else{

            return AcResult.ok(user);
        }

    }

    @ApiOperation(value="获取所有用户", notes="")
    @RequestMapping(value = "/user/", method= RequestMethod.GET)
    @ResponseBody public AcResult getAllUser() {
        List<User> list = userService.getAllUser();

        return AcResult.ok(list);
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
