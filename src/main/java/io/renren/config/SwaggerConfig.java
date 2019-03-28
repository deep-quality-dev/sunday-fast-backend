package io.renren.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 加了ApiOperation注解的类，才生成接口文档
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 包下的类，才生成接口文档
				// .apis(RequestHandlerSelectors.basePackage("io.renren.controller"))
				.paths(PathSelectors.any()).build().securitySchemes(security());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("星期天网络科技").description("酒店接口文档").termsOfServiceUrl("www.xqtinfo.cn").version("1.0.0").build();
	}

	private List<ApiKey> security() {
		return newArrayList(new ApiKey("token", "token", "header"));
	}

}