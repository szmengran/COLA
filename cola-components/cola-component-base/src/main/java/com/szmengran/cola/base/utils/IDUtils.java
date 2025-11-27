package com.szmengran.cola.base.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 雪花算法实现的ID生成工具类（优化版）
 *
 * <p>雪花算法生成的ID结构（64位）：
 * <ul>
 *   <li>1位：符号位，始终为0</li>
 *   <li>41位：时间戳（毫秒级），可使用约69年</li>
 *   <li>5位：数据中心ID，支持32个数据中心</li>
 *   <li>5位：机器ID，每个数据中心支持32台机器</li>
 *   <li>12位：序列号，每毫秒可生成4096个ID</li>
 * </ul>
 *
 * @author Joe
 * @date 2021/10/14 17:36
 * @version 2.0
 */
public class IDUtils {
    /**
     * 起始的时间戳（2018-08-01 00:00:00）
     */
    private static final long START_STMP = 1533052800000L;

    /**
     * 每一部分占用的位数
     */
    private static final long SEQUENCE_BIT = 12;    // 序列号占用的位数
    private static final long MACHINE_BIT = 5;      // 机器标识占用的位数
    private static final long DATACENTER_BIT = 5;   // 数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private static final long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);  // 31
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);        // 31
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);          // 4095

    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 时钟回拨容忍时间（毫秒）
     */
    private static final long CLOCK_BACKWARD_TOLERANCE = 5L;

    private long datacenterId;      // 数据中心ID
    private long machineId;         // 机器ID
    private long sequence = 0L;     // 序列号
    private long lastStmp = -1L;    // 上一次时间戳

    private volatile static IDUtils instance;

    /**
     * 获取IDUtils单例实例（使用默认配置：自动获取机器标识）
     *
     * @return IDUtils实例
     */
    public static IDUtils getInstance() {
        if (instance == null) {
            synchronized (IDUtils.class) {
                if (instance == null) {
                    // 自动获取机器标识
                    long workerId = getWorkerId();
                    long datacenterId = getDatacenterId();
                    instance = new IDUtils(datacenterId, workerId);
                }
            }
        }
        return instance;
    }

    /**
     * 获取IDUtils单例实例（使用指定的数据中心ID和机器ID）
     *
     * @param datacenterId 数据中心ID (0-31)
     * @param machineId    机器ID (0-31)
     * @return IDUtils实例
     */
    public static IDUtils getInstance(long datacenterId, long machineId) {
        if (instance == null) {
            synchronized (IDUtils.class) {
                if (instance == null) {
                    instance = new IDUtils(datacenterId, machineId);
                }
            }
        }
        return instance;
    }

    /**
     * 通过单例模式获取雪花ID（返回长整型）
     *
     * @return 雪花ID
     */
    public static long nextId() {
        return getInstance().generateNextId();
    }

    /**
     * 通过单例模式获取雪花ID（带类型前缀的字符串）
     *
     * @param type 类型前缀
     * @return 带前缀的ID字符串
     */
    public static String getSnowId(String type) {
        return type + nextId();
    }

    /**
     * 根据MAC地址获取机器ID
     *
     * @return 机器ID (0-31)
     */
    private static long getWorkerId() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                return 1L;
            }
            byte[] mac = network.getHardwareAddress();
            if (mac == null) {
                return 1L;
            }
            long id = ((0x000000FF & (long) mac[mac.length - 2])
                    | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
            return id % (MAX_MACHINE_NUM + 1);
        } catch (Exception e) {
            return 1L;
        }
    }

    /**
     * 根据IP地址获取数据中心ID
     *
     * @return 数据中心ID (0-31)
     */
    private static long getDatacenterId() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            byte[] ipAddr = ip.getAddress();
            long id = (((ipAddr[ipAddr.length - 2] & 0xFF) << 8)
                    | (ipAddr[ipAddr.length - 1] & 0xFF)) >> 6;
            return id % (MAX_DATACENTER_NUM + 1);
        } catch (Exception e) {
            return 1L;
        }
    }

    /**
     * 私有构造函数，防止外部实例化
     *
     * @param datacenterId 数据中心ID (0-31)
     * @param machineId    机器ID (0-31)
     */
    private IDUtils(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID（实例方法）
     *
     * @return 生成的雪花ID
     */
    private synchronized long generateNextId() {
        long currStmp = getCurrentTimestamp();

        // 处理时钟回拨
        if (currStmp < lastStmp) {
            long offset = lastStmp - currStmp;

            // 策略1：小幅回拨（≤容忍时间），使用上一个时间戳但检查序列号
            if (offset <= CLOCK_BACKWARD_TOLERANCE) {
                // 使用上一个时间戳继续生成
                currStmp = lastStmp;
                // 序列号必须自增，避免ID重复
                sequence = (sequence + 1) & MAX_SEQUENCE;

                // 如果序列号耗尽，必须等待到下一毫秒
                if (sequence == 0L) {
                    currStmp = waitNextMillis();
                    // 重置序列号
                    sequence = 0L;
                }
            }
            // 策略2：中等回拨（容忍时间 < offset ≤ 100ms），等待时钟追上
            else if (offset <= 100L) {
                try {
                    // 等待时钟追上
                    Thread.sleep(offset);
                    currStmp = getCurrentTimestamp();
                    if (currStmp < lastStmp) {
                        throw new RuntimeException(
                                String.format("Clock moved backwards after waiting. Refusing to generate id for %d milliseconds", offset));
                    }
                    sequence = 0L;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Clock callback thread interrupted", e);
                }
            }
            // 策略3：大幅回拨（>100ms），直接抛出异常
            else {
                throw new RuntimeException(
                        String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", offset));
            }
        }
        // 正常情况：当前时间 >= 上次时间
        else if (currStmp == lastStmp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = waitNextMillis();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT       // 数据中心部分
                | machineId << MACHINE_LEFT             // 机器标识部分
                | sequence;                              // 序列号部分
    }

    /**
     * 等待下一毫秒
     *
     * @return 下一毫秒的时间戳
     */
    private long waitNextMillis() {
        long mill = getCurrentTimestamp();
        while (mill <= lastStmp) {
            mill = getCurrentTimestamp();
        }
        return mill;
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳（毫秒）
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 解析雪花ID，提取各个组成部分
     *
     * @param id 雪花ID
     * @return ID信息对象
     */
    public static IdInfo parseId(long id) {
        long sequence = id & MAX_SEQUENCE;
        long machineId = (id >> MACHINE_LEFT) & MAX_MACHINE_NUM;
        long datacenterId = (id >> DATACENTER_LEFT) & MAX_DATACENTER_NUM;
        long timestamp = (id >> TIMESTMP_LEFT) + START_STMP;
        return new IdInfo(timestamp, datacenterId, machineId, sequence);
    }

    /**
     * 从雪花ID中提取生成时间
     *
     * @param id 雪花ID
     * @return 生成时间戳（毫秒）
     */
    public static long getTimestampFromId(long id) {
        return (id >> TIMESTMP_LEFT) + START_STMP;
    }

    /**
     * ID信息对象
     */
    public static class IdInfo {
        private final long timestamp;
        private final long datacenterId;
        private final long machineId;
        private final long sequence;

        public IdInfo(long timestamp, long datacenterId, long machineId, long sequence) {
            this.timestamp = timestamp;
            this.datacenterId = datacenterId;
            this.machineId = machineId;
            this.sequence = sequence;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getDatacenterId() {
            return datacenterId;
        }

        public long getMachineId() {
            return machineId;
        }

        public long getSequence() {
            return sequence;
        }

        @Override
        public String toString() {
            return "IdInfo{" +
                    "timestamp=" + timestamp +
                    " (" + new java.util.Date(timestamp) + ")" +
                    ", datacenterId=" + datacenterId +
                    ", machineId=" + machineId +
                    ", sequence=" + sequence +
                    '}';
        }
    }
}
