package com.szmengran.cola.extension.register;

import jakarta.annotation.Resource;

import com.szmengran.cola.extension.BizScenario;
import com.szmengran.cola.extension.ExtensionExecutor;
import com.szmengran.cola.extension.test.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ExtensionRegisterTest {

    @Resource
    private ExtensionRegister register;

    @Resource
    private ExtensionExecutor executor;

    @Test
    public void test() {
        SomeExtPt extA = new SomeExtensionA();
        register.doRegistration(extA);

        SomeExtPt extB = CglibProxyFactory.createProxy(new SomeExtensionB());
        register.doRegistration(extB);

        executor.executeVoid(SomeExtPt.class, BizScenario.valueOf("A"), SomeExtPt::doSomeThing);
        executor.executeVoid(SomeExtPt.class, BizScenario.valueOf("B"), SomeExtPt::doSomeThing);
    }

}
