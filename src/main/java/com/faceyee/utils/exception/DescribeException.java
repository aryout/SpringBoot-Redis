package com.faceyee.utils.exception;

import com.faceyee.utils.restful.RestServiceModel;

/**
 * Created by 97390 on 8/23/2018.
 */
public class DescribeException extends RuntimeException{

    private Integer code;

    /**
     * 继承exception，加入错误状态值
     * @param restStatusEnum
     */
    // 已知的enum构造excetion
    public DescribeException(RestServiceModel.RestStatusEnum restStatusEnum) {
        super(restStatusEnum.getMsg());
        this.code = restStatusEnum.getCode();
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