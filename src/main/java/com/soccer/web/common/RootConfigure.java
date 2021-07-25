package com.soccer.web.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("local")
@Configuration
public class RootConfigure implements WebMvcConfigurer{
	
	
	@Override
	public void addResourceHandlers (final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/video/**")
				.addResourceLocations("file:///C:/user/lsk/video/");
	}

}
