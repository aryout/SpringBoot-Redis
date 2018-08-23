package com.faceyee.utils.restful;

/**
 * Created by 97390 on 8/23/2018.
 */
public enum  ResultStatusEnum { // 其实,每个可期的注定抛出的异常,基本都可以自定义code,然后在这里建立enum
    // 枚举定义,用逗号相隔
    SUCCESS(0, "请求成功"),
    UNKNOW_ERROR(-1,"未知错误"),
    WEAK_NET_WORK(-2, "网络异常，请稍后重试"),
    USER_NOT_FIND(-101,"用户不存在"),
    PAGE_NOT_FOUND(-404,"找不到页面");

    private int code;
    private String msg;

    ResultStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
