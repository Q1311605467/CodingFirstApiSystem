package com.fjut.cf.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.fjut.cf.pojo.ResultJsonVO;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.redis.TokenManager;
import com.fjut.cf.redis.TokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author axiang [2019/10/9]
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    TokenManager manager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入用户登录拦截器");
        // 如果不是映射到方法上就直接跳过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired notLoginRequest = handlerMethod.getMethodAnnotation(LoginRequired.class);
        if (null == notLoginRequest) {
            return true;
        } else {
            // 从头部获取auth
            String auth = request.getHeader("auth");
            if (StringUtils.isEmpty(auth)) {
                ResultJsonVO ResultJsonVO = new ResultJsonVO();
                ResultJsonVO.setStatus(ResultJsonCode.USER_NOT_LOGIN, "请登录后重试");
                returnJson(response, JSONObject.toJSONString(ResultJsonVO));
                return false;
            }

            // 解析为token
            TokenModel model = manager.getToken(auth);
            if (manager.checkToken(model)) {
                return true;
            } else {
                ResultJsonVO ResultJsonVO = new ResultJsonVO();
                ResultJsonVO.setStatus(ResultJsonCode.USER_NOT_LOGIN, "请登录后重试");
                returnJson(response, JSONObject.toJSONString(ResultJsonVO));
                return false;
            }
        }
    }

    private void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}

