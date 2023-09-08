package com.szmengran.cola.gracefulshutdown.components.tomcat;

import com.szmengran.cola.gracefulshutdown.GracefulShutdown;
import com.szmengran.cola.gracefulshutdown.utils.GracefulShutdownOrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author MaoYuan.Li
 * @Date 2023/9/8 15:21
 * @Version 1.0
 */
@Slf4j
@Component
public class TomcatGracefulShutdown implements GracefulShutdown, TomcatConnectorCustomizer {

    private Connector connector;

    @Override
    public void shutdown() {
        log.debug("tomcat graceful shutdown...");
        if (connector == null) {
            return;
        }
        //关闭接收新请求
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();

        //先处理完tomcat正在处理的请求
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            threadPoolExecutor.shutdown();
            try {
                if (!threadPoolExecutor.awaitTermination(timeout(), TimeUnit.SECONDS)) {
                    log.warn("[Graceful Shutdown] Tomcat thread pool did not shut down gracefully within " + timeout() + " seconds. Proceeding with forceful shutdown");
                }
            } catch (InterruptedException ex) {
                log.error("[Graceful Shutdown] Tomcat thread pool graceful shutdown has been interrupted");
            }
        }
    }

    @Override
    public int order() {
        return GracefulShutdownOrderConstants.TOMCAT;
    }

    @Override
    public void customize(final Connector connector) {
        this.connector = connector;
    }

    public int timeout() {
        return 10;
    }
}
