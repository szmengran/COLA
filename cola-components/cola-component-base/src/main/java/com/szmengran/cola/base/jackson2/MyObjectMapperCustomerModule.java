package com.szmengran.cola.base.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * @Author MaoYuan.Li
 * @Date 2023/5/15 17:17
 * @Version 1.0
 */
public class MyObjectMapperCustomerModule extends SimpleModule {

    public MyObjectMapperCustomerModule() {
        super(MyObjectMapperCustomerModule.class.getName(), new Version(1, 0, 0, (String)null, (String)null, (String)null));
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(JwtAuthenticationToken.class, JwtAuthenticationTokenMixin.class);
    }

}
