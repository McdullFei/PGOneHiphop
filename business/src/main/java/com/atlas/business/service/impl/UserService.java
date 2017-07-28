package com.atlas.business.service.impl;

import com.atlas.business.dao.UserDao;
import com.atlas.business.dto.User;
import com.atlas.business.service.IUserService;
import com.atlas.framework.web.session.UserSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by renfei on 17/6/7.
 */
@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    UserSessionManager userSessionManager;

    @Override
    public void save() {

    }

    @Override
    public <T> T login() {
        List<User> list = userDao.getAll();
        logger.error("用户登录");
        return (T) list.get(0);
    }


    @Override
    public User getUserFromSession(String sessionId){

        return (User) userSessionManager.get(sessionId, User.class);
    }
}
