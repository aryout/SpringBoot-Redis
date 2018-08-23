package com.faceyee.utils.restful;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by 97390 on 8/23/2018.
 */
@Data // 不加这个的话,会抛出HttpMessageNotWritableException[要有getter方法]
public class ResultJson { // 封装响应报文
    private int code; // 状态码
    private String msg; // 描述

//    @JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
    @JsonInclude(JsonInclude.Include.NON_NULL) // 不加这个的话,会抛出HttpMessageNotWritableException[要排除null]
    private Object data; // 数据

     // 一些常见错误enum转化为ResultJson,废弃,直接转化为DescribeException然后抛出被拦截
    public ResultJson(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
    }

    public ResultJson(ResultStatusEnum resultStatusEnum, Object data) { // enum(成功),增加返回信息
        this(resultStatusEnum);
        this.data = data;
    }

    public ResultJson(int code, String msg){ // 主动抛出的可预见的用DescribeException包装的excetion 转化为ResultJson
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultJson{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
