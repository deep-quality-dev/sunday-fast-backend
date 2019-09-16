package io.renren.modules.hotel.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;

/**
 * 支持多公众号支付
 * 
 * @author taoz
 *
 */
@Component
//@ConditionalOnClass(WxPayService.class)
//@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
	private WxPayProperties properties;

	@Autowired
	public WxPayConfiguration(WxPayProperties properties) {
		this.properties = properties;
	}

	private static Map<String, WxPayService> payServices = Maps.newHashMap();

	@PostConstruct
	public void initServices() {
		final List<WxPayProperties.PayConfig> configs = properties.getConfigs();
		if (configs == null) {
			throw new RuntimeException();
		}
		payServices = configs.stream().map(a -> {
			WxPayConfig payConfig = new WxPayConfig();
			payConfig.setAppId(StringUtils.trimToNull(a.getAppId()));
			payConfig.setMchId(StringUtils.trimToNull(a.getMchId()));
			payConfig.setMchKey(StringUtils.trimToNull(a.getMchKey()));
			payConfig.setKeyPath(a.getKeyPath());
			// payConfig.setSubAppId(StringUtils.trimToNull());
			// payConfig.setSubMchId(StringUtils.trimToNull());
			// payConfig.setKeyPath(StringUtils.trimToNull());
			// 可以指定是否使用沙箱环境
			payConfig.setUseSandboxEnv(false);
			WxPayService wxPayService = new WxPayServiceImpl();
			wxPayService.setConfig(payConfig);
			return wxPayService;
		}).collect(Collectors.toMap(s -> s.getConfig().getAppId(), a -> a, (o, n) -> o));
	}

//	@Bean
//	@ConditionalOnMissingBean
//	public WxPayService wxService() {
//		WxPayConfig payConfig = new WxPayConfig();
//		payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
//		payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
//		payConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
//		payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
//		payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
//		payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));
//
//		// 可以指定是否使用沙箱环境
//		payConfig.setUseSandboxEnv(false);
//
//		WxPayService wxPayService = new WxPayServiceImpl();
//		wxPayService.setConfig(payConfig);
//		return wxPayService;
//	}

	public static Map<String, WxPayService> getPayServices() {
		return payServices;
	}
}
