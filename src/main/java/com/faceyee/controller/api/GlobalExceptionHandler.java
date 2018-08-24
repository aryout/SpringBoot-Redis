package com.faceyee.controller.api;

import com.faceyee.utils.readConfig.LocaleMessageUtil;
import com.faceyee.utils.exception.DescribeException;
import com.faceyee.utils.restful.RestServiceError;
import com.faceyee.utils.restful.RestServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Created by 97390 on 8/24/2018.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    LocaleMessageUtil localeMessageUtil;


    // 根据特定的异常返回指定的 HTTP 状态码
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public RestServiceError handleValidationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : errors) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return localeMessageUtil.getLocalErrorMessage(RestServiceError.Type.VALIDATION_ERROR);
    }

    // 未知错误，返回500
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestServiceError handleException(Exception ex) {
        return localeMessageUtil.getLocalErrorMessage(RestServiceError.Type.INTERNAL_SERVER_ERROR);
    }

    /**
     * 已知错误，记录在log中
     * @param de
     * @return
     */
    @ExceptionHandler(value = DescribeException.class)
    @ResponseBody
    public RestServiceModel exceptionGet(DescribeException de) throws Exception {
        // 如果碰到了某个自定义异常加上了@ResponseStatus，就继续抛出，这样就不会让自定义异常失去加上@ResponseStatus的初衷。
        if (AnnotationUtils.findAnnotation(de.getClass(), ResponseStatus.class) != null){
            throw de;
        }
        LOGGER.error("【运行异常】{}",de);
        return new RestServiceModel(false, de.getCode(),de.getMessage(), null);
    }
}