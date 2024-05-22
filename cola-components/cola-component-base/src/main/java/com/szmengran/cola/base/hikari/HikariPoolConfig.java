package com.szmengran.cola.base.hikari;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/10/8 21:13
 */
@Slf4j
@Configuration
public class HikariPoolConfig implements ApplicationRunner {

    @Resource
    private DataSource dataSource;

    /**
     * BUG FIXED for new version HikariPool can't init
     * @param args
     * @Return void
     * @Date: 2023/10/8 21:45
     * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("hikari pool initializing...");
        dataSource.getConnection();
    }
}
