package com.atlas.business.service;

/**
 * 用户登录接口
 *
 * Created by renfei on 17/6/7.
 */
public interface Login {
    <T> T login(String username, String password);
}
