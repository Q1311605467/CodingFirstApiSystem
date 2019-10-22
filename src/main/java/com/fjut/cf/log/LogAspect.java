package com.fjut.cf.log;

import com.fjut.cf.pojo.ResultJsonVO;
import com.fjut.cf.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * AOP切片类
 * 目前仅打印Controller层的方法
 *
 * @author axiang [2019/10/22]
 */
@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 切点为controller层的所有方法，  ..表示包和子包
     */
    @Pointcut("execution(public * com.fjut.cf.controller..*.*(..))")
    public void controllerMethod() {
    }

    @Before("controllerMethod()")
    public void logBeforeRequest(JoinPoint joinPoint) throws Exception {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        StringBuilder requestLog = new StringBuilder();
        requestLog.append("\n=============进入Controller=============\n");
        requestLog.append("请求信息：").append("URL = {").append(request.getRequestURI()).append("},\n")
                .append("HTTP_METHOD = {").append(request.getMethod()).append("},\n")
                .append("IP = {").append(IpUtils.getClientIpAddress(request)).append("},\n")
                .append("CLASS_METHOD = {").append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName()).append("},\n");

        if (joinPoint.getArgs().length == 0) {
            requestLog.append("ARGS = {} ");
        } else {
            requestLog.append("ARGS = {");
            Object[] paramValues = joinPoint.getArgs();
            String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
            for (int i = 0; i < paramNames.length; i++) {
                requestLog.append(paramNames[i]).append(": ").append(paramValues[i]).append(", ");
            }
            requestLog.append("}\n");
        }
        logger.info(requestLog.toString());
    }

    @AfterReturning(returning = "resultJsonVO", pointcut = "controllerMethod()")
    public void logAfterReturning(ResultJsonVO resultJsonVO) {
        String requestLog = "\n请求结果：" + resultJsonVO.toString() +
                "\n=============离开Controller=============\n";
        logger.info(requestLog);

    }

}
