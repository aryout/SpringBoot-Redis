package com.faceyee.utils.exception;

import com.faceyee.utils.restful.ResultStatusEnum;

/**
 * Created by 97390 on 8/23/2018.
 */
public class DescribeException extends RuntimeException{

    private Integer code;
    private String message;

    /**
     * 继承exception，加入错误状态值
     * @param resultStatusEnum
     */
    // 已知的异常enum构造excetion
    public DescribeException(ResultStatusEnum resultStatusEnum) {
        super(resultStatusEnum.getMsg());
        this.code = resultStatusEnum.getCode();
    }

    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    // 运行时,自定义抛出动态构造的excetion
    public DescribeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}