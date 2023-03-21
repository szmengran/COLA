package com.szmengran.cola.extension.register;

import com.szmengran.cola.extension.Extension;

import org.springframework.stereotype.Component;

@Extension(bizId = "A")
@Component
public class SomeExtensionA implements SomeExtPt {

    @Override
    public void doSomeThing() {
        System.out.println("SomeExtensionA::doSomething");
    }

}
