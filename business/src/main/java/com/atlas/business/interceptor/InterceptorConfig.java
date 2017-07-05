package com.atlas.business.interceptor;

import com.atlas.framework.web.VerifySignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by renfei on 17/6/22.
 */
@Configuration
public class InterceptorConfig {
    @Bean
    public VerifySignInterceptor vrifySignInterceptor(){
        VerifySignInterceptor interceptor = new VerifySignInterceptor();

        return interceptor;
    }
}
