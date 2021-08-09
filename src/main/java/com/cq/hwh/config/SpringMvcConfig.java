package com.cq.hwh.config;

import com.cq.hwh.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * spring mvc配置
 *
 * @author 程崎
 * @date 2021/07/26
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 登录拦截器
     */
    @Resource
    LoginInterceptor loginInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/test/**",
                        "/user/login",
                        "/user/save",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**"
                );
    }
}
