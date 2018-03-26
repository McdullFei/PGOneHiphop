package com.atlas.business.service.impl;

import com.atlas.business.dao.UserDao;
import com.atlas.business.repository.UserRepository;
import com.atlas.business.dto.User;
import com.atlas.business.service.IUserService;
import com.atlas.framework.exception.PGException;
import com.atlas.framework.web.session.UserSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private UserRepository userRepository;

    @Override
    public void save() {

    }

    @Override
    public <T> T login(String username, String password) {
        logger.error("用户登录");
        return (T) userRepository.findUserByUsernameAndPassword(username, password);
    }


    @Override
    public User getUserFromSession(String sessionId){

        return (User) userSessionManager.get(sessionId, User.class);
    }

    @Override
    public User getUserById(Long uid) {
        if(uid.longValue()==2){
            throw new PGException("故意抛出一个异常~~~");

        }
        return userRepository.findUserById(uid);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAll();
    }
}
