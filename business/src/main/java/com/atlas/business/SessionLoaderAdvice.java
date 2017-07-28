package com.atlas.business;

import com.atlas.business.dto.User;
import com.atlas.framework.common.JsonSerializer;
import com.atlas.framework.web.session.UserSessionManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by renfei on 17/6/6.
 */
@Aspect
@Component
public class SessionLoaderAdvice {
    @Autowired
    private UserSessionManager<User> userSessionManager;

    @AfterReturning(pointcut = "execution(* com.atlas.business.service.Login.login(..))",
            returning="returnValue")
    public void userSession(JoinPoint point, Object returnValue) {
        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
        userSessionManager.set((User)returnValue);
    }
}
