package com.faceyee.utils.exception;

/**
 * Created by 97390 on 8/23/2018.
 */
public class PageNotFoundExcetion extends DescribeException {
    private static final long serialVersionUID = -1279510255938492931L;


    public PageNotFoundExcetion(int code, String message){
        super(code, message);
    }
}