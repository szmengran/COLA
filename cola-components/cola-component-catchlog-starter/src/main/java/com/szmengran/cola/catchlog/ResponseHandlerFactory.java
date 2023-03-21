package com.szmengran.cola.catchlog;

public class ResponseHandlerFactory {

    public static ResponseHandlerI get(){
        if(ApplicationContextHelper.getBean(ResponseHandlerI.class) != null){
            return ApplicationContextHelper.getBean(ResponseHandlerI.class);
        }
        return new DefaultResponseHandler();
    }
}
