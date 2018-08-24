package com.faceyee.utils.restful;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by 97390 on 8/23/2018.
 */
@Data // 不加这个的话,会抛出HttpMessageNotWritableException[要有getter方法]
public class RestServiceModel { // 封装响应报文
    private int code; // 状态码
    private boolean status = true; // 状态
    private String msg; // 描述

//    @JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
    @JsonInclude(JsonInclude.Include.NON_NULL) // 不加这个的话,会抛出HttpMessageNotWritableException[要排除null]
    private Object data; // 数据

    public RestServiceModel(){}

    public RestServiceModel(RestStatusEnum restStatusEnum) {
        this.code = restStatusEnum.getCode();
        this.msg = restStatusEnum.getMsg();
        this.status = restStatusEnum.getStatus();
    }

    // 已有enum 填充
    public RestServiceModel(RestStatusEnum restStatusEnum, Object data) {
        this(restStatusEnum);
        this.data = data;
    }

    // 自定义
    public RestServiceModel(boolean status, int code, String msg, Object data){
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\":" + status +
                ", \"code\":" + code +
                ", \"msg\":" + msg  +
                ", \"data\":" + data +
                "}";
    }


    public enum  RestStatusEnum {
        // 枚举定义,用逗号相隔
        SUCCESS(true, 200, "请求成功"),
        USER_NOT_FIND(false, -101,"用户不存在");

        private boolean status;
        private int code;
        private String msg;

        RestStatusEnum(boolean status, int code, String msg) {
            this.status = status;
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public boolean getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

    }
}
