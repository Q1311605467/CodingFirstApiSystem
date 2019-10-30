package com.fjut.cf.component.handler;

import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author axiang [2019/10/9]
 */
@RestControllerAdvice(basePackages = {"com.fjut.cf.controller.*"},
        annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 400 - 请求参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, TypeMismatchException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResultJsonVO conversionErrorHandler(Exception ex) {
        logger.error("请求参数异常！", ex);
        return new ResultJsonVO(ResultJsonCode.BAD_REQUEST, "请求参数异常！");
    }


    /**
     * FIXME: 405处理不生效，由默认的 DefaultHandlerExceptionResolver 处理
     * 405 - 请求不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultJsonVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ResultJsonVO resultJson = new ResultJsonVO();
        String exceptionMessage = "请求方法不支持！";
        resultJson.setStatus(ResultJsonCode.METHOD_NOT_SUPPORTED, exceptionMessage);
        return resultJson;
    }

    /**
     * 500 - 服务器内部错误
     *
     * @param e 异常
     * @return 返回json
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultJsonVO handleException(Exception e) {
        ResultJsonVO resultJson = new ResultJsonVO();
        String exceptionMessage;
        if (e instanceof ArrayIndexOutOfBoundsException) {
            exceptionMessage = "数组越界错误";
        } else if (e instanceof NullPointerException) {
            exceptionMessage = "空指针错误！";
        } else if (e instanceof RuntimeException) {
            exceptionMessage = "运行时系统错误";
        } else {
            exceptionMessage = "其他类型系统错误";
        }
        resultJson.setStatus(ResultJsonCode.SYSTEM_ERROR, "系统内部错误！");
        logger.error(exceptionMessage, e);
        return resultJson;
    }

}
