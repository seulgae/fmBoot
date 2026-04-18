package com.ucamp.fm.common.config;

import com.ucamp.fm.common.interceptor.LoginMemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 공통 인터셉터 등록 등 MVC 전역 설정을 담당한다.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginMemberInterceptor loginMemberInterceptor;

    public WebMvcConfig(LoginMemberInterceptor loginMemberInterceptor) {
        this.loginMemberInterceptor = loginMemberInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginMemberInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/uploadImg/**",
                        "/h2-console/**"
                );
    }
}
