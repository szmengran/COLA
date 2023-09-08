package com.szmengran.cola.gracefulshutdown;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.DubboShutdownHook;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author MaoYuan.Li
 * @Date 2023/9/8 11:25
 * @Version 1.0
 */
@Slf4j
@Component
public class GracefulShutdownListener implements ApplicationListener, ApplicationContextAware {

    private AtomicBoolean destroyed = new AtomicBoolean(false);

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            onContextRefreshedEvent();
        } else if (event instanceof ContextClosedEvent) {
            onContextClosedEvent();
        }
    }

    private void onContextRefreshedEvent() {
        new DubboShutdownHook(ApplicationModel.defaultModel()).unregister();
    }

    private void onContextClosedEvent() {
        // 保证只被执行一次
        if (!destroyed.compareAndSet(false, true)) {
            return;
        }

        Map<String, GracefulShutdown> map = applicationContext.getBeansOfType(GracefulShutdown.class);
        Collection<GracefulShutdown> values = map.values();
        List<GracefulShutdown> list = new ArrayList<>(values);
        Collections.sort(list, Comparator.comparing(GracefulShutdown::order));

        list.forEach(gracefulShutdown -> {
            long startTime = System.currentTimeMillis();
            gracefulShutdown.shutdown();
            log.info("graceful shutdown bean complete {}, cost:{}", gracefulShutdown.getClass().getSimpleName(), (System.currentTimeMillis() - startTime));
        });
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
