package com.szmengran.cola.catchlog;


import com.szmengran.cola.dto.Response;
import com.szmengran.cola.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * ResponseHandler
 *
 * @author Frank Zhang
 * @date 2020-11-10 3:18 PM
 */
@Slf4j
public class DefaultResponseHandler implements ResponseHandlerI{

    @Override
    public  Object handle(Class returnType, String errCode, String errMsg){
        if (isColaResponse(returnType)){
            return handleColaResponse(returnType, errCode, errMsg);
        }
        return null;
    }

    public  Object handle(Class returnType, BaseException e){
        return handle(returnType, e.getErrCode(), e.getMessage());
    }


    private static Object handleColaResponse(Class returnType, String errCode, String errMsg) {
        try {
            Response response = (Response)returnType.newInstance();
            response.setSuccess(false);
            response.setErrCode(errCode);
            response.setErrMessage(errMsg);
            return response;
        }
        catch (Exception ex){
            log.error(ex.getMessage(), ex);
            return  null;
        }
    }

    private static boolean isColaResponse(Class returnType) {
        return  returnType == Response.class || returnType.getGenericSuperclass() == Response.class;
    }
}
