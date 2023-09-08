package com.szmengran.cola.gracefulshutdown.components.dubbo;

import com.szmengran.cola.gracefulshutdown.GracefulShutdown;
import com.szmengran.cola.gracefulshutdown.utils.GracefulShutdownOrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.registry.support.RegistryManager;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author MaoYuan.Li
 * @Date 2023/9/8 14:18
 * @Version 1.0
 */
@Slf4j
@Component
public class DubboGracefulShutDown implements GracefulShutdown {

    @Override
    public void shutdown() {
        log.debug("dubbo graceful shutdown...");
        RegistryManager registryManager = RegistryManager.getInstance(ApplicationModel.defaultModel());
        if (Objects.nonNull(registryManager)) {
            registryManager.destroyAll();
        }
    }

    @Override
    public int order() {
        return GracefulShutdownOrderConstants.DUBBO;
    }
}
