package com.fjut.cf.handler;

import com.fjut.cf.pojo.ResultJsonVO;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author axiang [2019/10/9]
 */
@RestControllerAdvice(basePackages = {"com.fjut.cf.controller.*"},
        annotations = {Controller.class, RestController.class})
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultJsonVO handleException(Exception e) {
        if (e instanceof RuntimeException) {
            logger.error("运行时错误", e);
        } else {
            logger.error("其他错误", e);
        }
        ResultJsonVO ResultJsonVO = new ResultJsonVO();
        ResultJsonVO.setStatus(ResultJsonCode.SYSTEM_ERROR, "系统错误！");
        return ResultJsonVO;
    }

}
