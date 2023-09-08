package com.szmengran.cola.gracefulshutdown;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author MaoYuan.Li
 * @Date 2023/9/8 15:37
 * @Version 1.0
 */
@ComponentScan("com.szmengran.cola.gracefulshutdown")
@ConditionalOnProperty(prefix = "graceful.shutdown", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GracefulShutdownAutoConfiguration {

}
