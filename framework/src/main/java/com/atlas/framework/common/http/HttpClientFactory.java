package com.atlas.framework.common.http;

import org.apache.commons.codec.Charsets;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

/**
 * httpclient 示例
 * Created by renfei on 17/6/8.
 */
public class HttpClientFactory implements FactoryBean<HttpClient>{

    private static final int DEFAULT_MAX_TOTAL = 512; //最大支持的连接数
    private static final int DEFAULT_MAX_PER_ROUTE = 64; //针对某个域名的最大连接数

    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000; //跟目标服务建立连接超时时间，根据自己的业务调整
    private static final int DEFAULT_SOCKET_TIMEOUT = 3000; //请求的超时时间（建联后，获取response的返回等待时间）
    private static final int DEFAULT_TIMEOUT = 1000; //从连接池中获取连接的超时时间

    @Override
    public HttpClient getObject() throws Exception {
        ConnectionConfig config = ConnectionConfig.custom()
                .setCharset(Charsets.UTF_8)
                .build();

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                .build();

        return HttpClients.custom()
                .setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE)
                .setMaxConnTotal(DEFAULT_MAX_TOTAL)
                .setRetryHandler((exception, executionCount, context) -> executionCount <= 3 && (exception instanceof NoHttpResponseException
                        || exception instanceof ClientProtocolException
                        || exception instanceof SocketTimeoutException
                        || exception instanceof ConnectTimeoutException))
                .setDefaultConnectionConfig(config)
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
