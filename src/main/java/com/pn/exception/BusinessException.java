package com.pn.exception;

/**
 * 自定义的运行时异常
 */
public class BusinessException extends RuntimeException{
    //只是创建异常对象
    public BusinessException() {
        super();
    }

    public BusinessException(String message){
        super(message);
    }

}
