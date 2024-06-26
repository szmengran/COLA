package com.szmengran.cola.base.token;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/12 20:12
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class ResourceSecurityConfig {

    @Resource
    private Oauth2Properties oauth2Properties;

    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(oauth2Properties.getJwt().getUrl()).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(oauth2Properties.getJwt().getIgnoreUrls()).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

}
