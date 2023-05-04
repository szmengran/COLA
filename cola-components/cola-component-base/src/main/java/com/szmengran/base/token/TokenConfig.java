package com.szmengran.base.token;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/28 19:22
 * @Version 1.0
 */
@Configuration
public class TokenConfig {

    @Resource
    private JwtProperties jwtProperties;

    @Bean
    public JwtDecoder nimbusJwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwtProperties.getPublicKey()).build();
    }

}
