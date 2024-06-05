package com.szmengran.cola.exception.controller;

import java.io.IOException;
import java.util.List;

import com.szmengran.cola.dto.Response;
import com.szmengran.cola.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理
 * @author Joe
 * @date 2021/10/12 11:12
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    // 无法反序列化异常处理
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Response requestNotReadable(HttpMessageNotReadableException e) {
        log.error("", e);
        return Response.buildFailure("HTTP_MESSAGE_NOT_READABLE_EXCEPTION", e.getMessage());
    }

    // 参数不匹配异常处理
    @ExceptionHandler({TypeMismatchException.class})
    public Response requestTypeMismatch(TypeMismatchException e) {
        log.error("", e);
        return Response.buildFailure("TYPE_MISMATCH_EXCEPTION", e.getMessage());
    }

    // 请求的参数不合法处理
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response requestMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String msg = objectError.getDefaultMessage();
        log.warn("REQUEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION：{}", msg);
        return Response.buildFailure("REQUEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION", msg);
    }

    // 缺少请求参数异常处理
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Response requestMissingServletRequest(MissingServletRequestParameterException e) {
        log.error("", e);
        return Response.buildFailure("MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION", e.getMessage());
    }

    // 请求参数异常处理
    @ExceptionHandler({BindException.class})
    public Response requestBindException(BindException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String msg = objectError.getDefaultMessage();
        log.error("", msg);
        return Response.buildFailure("BIND_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    public Response request401() {
        return Response.buildFailure("AUTHENTICATION_EXCEPTION", "认证异常");
    }

    // 请求方法不支持异常处理
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Response request405(){
        return Response.buildFailure("HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION", "请求方法不支持异常处理");
    }

    // MediaType转化错误处理
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public Response request406(){
        return Response.buildFailure("HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION", "MediaType转化错误处理");
    }

    // 程序运行时异常处理
    @ExceptionHandler({ConversionNotSupportedException.class})
    public Response server500(ConversionNotSupportedException e){
        log.error("", e);
        return Response.buildFailure("CONVERSION_NOT_SUPPORTED_EXCEPTION", e.getMessage());
    }

    // 程序运行时异常处理
    @ExceptionHandler({HttpMessageNotWritableException.class})
    public Response server500(HttpMessageNotWritableException e){
        log.error("", e);
        return Response.buildFailure("HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION", e.getMessage());
    }

    // 程序运行时异常处理
    @ExceptionHandler({RuntimeException.class})
    public Response server500(RuntimeException e){
        log.error("", e);
        return Response.buildFailure("RUNTIME_EXCEPTION", e.getMessage());
    }

    // 空指针异常处理
    @ExceptionHandler(NullPointerException.class)
    public Response nullPointerExceptionHandler(NullPointerException e) {
        log.error("", e);
        return Response.buildFailure("NULL_POINTER_EXCEPTION", e.getMessage());
    }

    // 类型转换异常处理
    @ExceptionHandler(ClassCastException.class)
    public Response classCastExceptionHandler(ClassCastException e) {
        log.error("", e);
        return Response.buildFailure("CLASS_CAST_EXCEPTION", e.getMessage());
    }

    // IO异常处理
    @ExceptionHandler(IOException.class)
    public Response iOExceptionHandler(IOException e) {
        log.error("", e);
        return Response.buildFailure("IO_EXCEPTION", e.getMessage());
    }

    // 未知方法异常处理
    @ExceptionHandler(NoSuchMethodException.class)
    public Response noSuchMethodExceptionHandler(NoSuchMethodException e) {
        log.error("", e);
        return Response.buildFailure("NO_SUCH_METHOD_EXCEPTION", e.getMessage());
    }

    // 数组越界异常处理
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Response indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e) {
        log.error("", e);
        return Response.buildFailure("INDEX_OUT_OF_BOUNDS_EXCEPTION", e.getMessage());
    }

    // 自定义异常处理
    @ExceptionHandler({BizException.class})
    public Response bizException(BizException e) {
        if (log.isDebugEnabled()) {
            log.error("", e);
        } else {
            log.warn("BIZ EXCEPTION : {} ", e.getMessage());
        }
        return Response.buildFailure(e.getErrCode(), e.getMessage());
    }

    // 异常处理
    @ExceptionHandler({Exception.class})
    public Response exception(Exception e) {
        log.error("", e);
        return Response.buildFailure("EXCEPTION", e.getMessage());
    }
}
