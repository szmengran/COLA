//package com.szmengran.cola.base.jackson2;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
//import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//
///**
// * @Author MaoYuan.Li
// * @Date 2023/5/15 17:11
// * @Version 1.0
// */
//@JsonTypeInfo(
//        use = Id.CLASS,
//        include = As.PROPERTY,
//        property = "@class"
//)
//@JsonAutoDetect(
//        fieldVisibility = Visibility.ANY,
//        getterVisibility = Visibility.NONE,
//        isGetterVisibility = Visibility.NONE
//)
//@JsonDeserialize(
//        using = JwtAuthenticationTokenDeserializer.class
//)
//public abstract class JwtAuthenticationTokenMixin {
//    JwtAuthenticationTokenMixin() {
//    }
//}
