package com.fjut.cf.config;

import com.fjut.cf.component.interceptor.LoginRequestInterceptor;
import com.fjut.cf.component.interceptor.PrivateRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC控制器配置
 *
 * @author axiang [2019/10/9]
 */
@Configuration
public class ControllerConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginRequestInterceptor loginRequestInterceptor;

    @Autowired
    private PrivateRequestInterceptor privateRequestInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns("/**");
        registry.addInterceptor(privateRequestInterceptor).addPathPatterns("/**");

    }
}
