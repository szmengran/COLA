package com.szmengran.cola.gracefulshutdown;

/**
 * @Author MaoYuan.Li
 * @Date 2023/9/8 11:37
 * @Version 1.0
 */
public interface GracefulShutdown {

    /**
     * @description: 优雅停机
     * @param
     * @return: void
     * @author MaoYuan.Li
     * @date: 2023/9/8 11:38
     */
    void shutdown();

    /**
     * @description: 排序
     * @param
     * @return: int
     * @author MaoYuan.Li
     * @date: 2023/9/8 11:42
     */
    default int order() {
        return 0;
    }

    /**
     * @description: 超时时间
     * @param
     * @return: int
     * @author MaoYuan.Li
     * @date: 2023/9/8 12:12
     */
    default int timeout() {
        return -1;
    }
}
