//package com.szmengran.cola.base.jackson2;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
//import java.io.IOException;
//
///**
// * @Author MaoYuan.Li
// * @Date 2023/5/15 16:45
// * @Version 1.0
// */
//public class JwtAuthenticationTokenDeserializer extends JsonDeserializer<JwtAuthenticationToken> {
//
//    @Override
//    public JwtAuthenticationToken deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JacksonException {
//        Jwt jwt = jsonParser.readValueAs(Jwt.class);
//        return new JwtAuthenticationToken(jwt);
//    }
//
//}
