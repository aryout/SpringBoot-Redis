package com.faceyee.utils.exception;

import com.faceyee.utils.restful.ResultJson;
import com.faceyee.utils.restful.ResultStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 97390 on 8/23/2018.
 */
@ControllerAdvice // 定义全局异常处理类
public class GlobleExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobleExceptionHandler.class);

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(PageNotFoundExcetion.class) // 被包含在下面的方法中,冲突怎么办呢?
    ModelAndView handleBusinessException(PageNotFoundExcetion e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message",e.getMessage());
        mv.setViewName("errorPage/404");
        return mv;
    }

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultJson exceptionGet(Exception e){
        if(e instanceof DescribeException){
            DescribeException MyException = (DescribeException) e;
            return new ResultJson(MyException.getCode(),MyException.getMessage());
        }

        LOGGER.error("【系统异常】{}",e);
        return new ResultJson(ResultStatusEnum.UNKNOW_ERROR); // 既不在enum中定义的(非UNKNOW_ERROR),也不在运行时动态定义的,由spring产生的异常,打印后,标记为未知异常
    }
}