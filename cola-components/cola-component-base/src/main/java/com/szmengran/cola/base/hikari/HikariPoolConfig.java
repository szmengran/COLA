package com.szmengran.cola.base.hikari;

import java.sql.Connection;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/10/8 21:13
 */
@Slf4j
@Configuration
public class HikariPoolConfig {

    /**
     * BUG FIXED for new version HikariPool can't init
     * @param dataSource
     * @Return org.springframework.boot.ApplicationRunner
     * @Date: 2023/10/8 21:20
     * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
     */
    @Bean
    public ApplicationRunner runner(DataSource dataSource) {
        return args -> {
            log.info("hikari pool initializing...");
            Connection connection = dataSource.getConnection();
        };
    }
}
