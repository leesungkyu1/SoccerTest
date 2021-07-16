package com.soccer.web.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.soccer.web.common.interceptor.AdminAuthorityInterceprot;
import com.soccer.web.common.interceptor.LoginAuthorityInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//로그인 권한 인터셉터
		registry.addInterceptor(new LoginAuthorityInterceptor())
			.excludePathPatterns("적용할 주소");
		
		//관리자 체크 인터셉터
		registry.addInterceptor(new AdminAuthorityInterceprot())
			.excludePathPatterns("적용할 주소");
	}
}
