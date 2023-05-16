package com.szmengran.cola.base.jackson2;

import org.apache.dubbo.spring.security.jackson.ObjectMapperCodec;
import org.apache.dubbo.spring.security.jackson.ObjectMapperCodecCustomer;

/**
 * @Author MaoYuan.Li
 * @Date 2023/5/15 17:17
 * @Version 1.0
 */
public class MyObjectMapperCustomer implements ObjectMapperCodecCustomer {

    @Override
    public void customize(ObjectMapperCodec objectMapperCodec) {
        objectMapperCodec.addModule(new MyObjectMapperCustomerModule());
    }

}
