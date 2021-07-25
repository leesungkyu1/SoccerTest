package com.soccer.web.common;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.soccer.web.common.interceptor.AdminAuthorityInterceprot;
import com.soccer.web.common.interceptor.LoginAuthorityInterceptor;
import com.soccer.web.common.interceptor.UserAutoInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Bean
	public FilterRegistrationBean<Filter> getFilterRegistrationBean() {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>(new HiddenHttpMethodFilter());
		registrationBean.setOrder(1);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//로그인 권한 인터셉터
//		registry.addInterceptor(new LoginAuthorityInterceptor())
//			.addPathPatterns("/user/login");
//		
//		//로그인 했는지 체크
//		registry.addInterceptor(new UserAutoInterceptor())
//			.excludePathPatterns("/", "/css/*", "/js/*", "/img/*", "/user/login/view", "/user/login");
//		
		//관리자 체크 인터셉터
		registry.addInterceptor(new AdminAuthorityInterceprot())
			.addPathPatterns("적용할 주소");
	}
}
