package com.atlas.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 *
 * 启动加载器,可以处理一些需要启动加载的方法
 *
 * Created by renfei on 17/6/5.
 */
@Component
@Order(value=10000)
public class StartupRunner implements CommandLineRunner {
    protected static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private DataSource ds;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Datasource: " + ds.toString());
    }
}
