package com.atlas.business.service;


import com.atlas.business.dto.User;

/**
 * Created by renfei on 17/6/7.
 */
public interface IUserService extends Login {
    void save();

    User getUserFromSession(String sessionId);
}
