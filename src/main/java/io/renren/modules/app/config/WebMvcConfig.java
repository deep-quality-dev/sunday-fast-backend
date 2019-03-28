/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.renren.modules.app.interceptor.AuthorizationInterceptor;
import io.renren.modules.app.resolver.LoginUserHandlerMethodArgumentResolver;

/**
 * MVC配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;
	@Autowired
	private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**").addPathPatterns("/*/hotel/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
	}
}