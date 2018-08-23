package com.faceyee.utils.exception;

/**
 * Created by 97390 on 8/23/2018.
 */
public class BusinessException extends DescribeException { // 交易事务失败

    private static final long serialVersionUID = -2052344358836723453L;

    public BusinessException(int code, String message){
        super(code, message);
    }
}