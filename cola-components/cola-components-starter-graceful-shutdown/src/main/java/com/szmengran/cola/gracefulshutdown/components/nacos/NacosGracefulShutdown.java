package com.szmengran.cola.gracefulshutdown.components.nacos;

import com.szmengran.cola.gracefulshutdown.GracefulShutdown;
import com.szmengran.cola.gracefulshutdown.utils.GracefulShutdownOrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

/**
 * @Author MaoYuan.Li
 * @Date 2023/9/8 11:29
 * @Version 1.0
 */
@Slf4j
@Component
public class NacosGracefulShutdown implements GracefulShutdown, ApplicationContextAware {

    private Map<String, AbstractAutoServiceRegistration>  abstractAutoServiceRegistrations;

    @Override
    public void shutdown() {
        log.debug("nacos graceful shutdown...");
        if (!CollectionUtils.isEmpty(abstractAutoServiceRegistrations)) {
            Set<String> set = abstractAutoServiceRegistrations.keySet();
            set.forEach(key -> {
                AbstractAutoServiceRegistration abstractAutoServiceRegistration = abstractAutoServiceRegistrations.get(key);
                abstractAutoServiceRegistration.stop();
            });
        }
    }

    @Override
    public int order() {
        return GracefulShutdownOrderConstants.NACOS;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.abstractAutoServiceRegistrations = applicationContext.getBeansOfType(AbstractAutoServiceRegistration.class);
    }
}
