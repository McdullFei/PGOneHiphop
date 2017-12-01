package com.atlas.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atlas.framework.web.VerifySignInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by renfei on 17/6/22.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private VerifySignInterceptor verifySignInterceptor;

    @Autowired
    private MappingJackson2HttpMessageConverter httpMessageConverter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(verifySignInterceptor);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter() {
            // 重写 writeInternal 方法，在返回内容前首先进行加密
            @Override
            protected void writeInternal(Object object,
                                         HttpOutputMessage outputMessage) throws IOException,
                    HttpMessageNotWritableException {
                // 使用 Jackson 的 ObjectMapper 将 Java 对象转换成 Json String
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(object);
                JSONObject requestBodyJson = JSON.parseObject(json);
                requestBodyJson.put("hhh", "新家的字段");

                // 输出
                outputMessage.getBody().write(requestBodyJson.toJSONString().getBytes("UTF-8"));
            }
        };
    }

    /**
     * 重写报文返回的转换器
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}
