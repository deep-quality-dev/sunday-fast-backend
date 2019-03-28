package io.renren.modules.hotel.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConditionalOnExpression("!'${sms.aliyun}'.isEmpty()")
@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsAliyunPropertiesConfig {
	/**
	 * 应用ID
	 */
	private String accessKey;

	/**
	 * 应用秘钥
	 */
	private String secretKey;

	/**
	 * 短信模板配置
	 */
	private Map<String, String> channels;
}
