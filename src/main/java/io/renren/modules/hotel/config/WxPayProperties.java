package io.renren.modules.hotel.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.service.HotelWxConfigService;
import lombok.Data;

/**
 * wxpay pay properties
 *
 * @author Binary Wang
 */

@Data
@Component
//@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
	@Autowired
	private HotelWxConfigService hotelWxConfigService;

	private List<PayConfig> configs;

	@PostConstruct
	public void init() {
		PayConfig config = null;
		List<PayConfig> configs = new ArrayList<WxPayProperties.PayConfig>();
		List<HotelWxConfigEntity> configEntities = hotelWxConfigService.list();
		for (HotelWxConfigEntity hotelWxConfigEntity : configEntities) {
			config = new PayConfig();
			config.setAppId(hotelWxConfigEntity.getAppId());
			config.setMchId(hotelWxConfigEntity.getMchId());
			config.setMchKey(hotelWxConfigEntity.getMchKey());
			config.setKeyPath(hotelWxConfigEntity.getKeyPath());
			configs.add(config);
		}
		this.setConfigs(configs);
	}

	@Data
	public class PayConfig {
		/**
		 * 设置微信公众号或者小程序等的appid
		 */
		private String appId;

		/**
		 * 微信支付商户号
		 */
		private String mchId;

		/**
		 * 微信支付商户密钥
		 */
		private String mchKey;

		/**
		 * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
		 */
		private String subAppId;

		/**
		 * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
		 */
		private String subMchId;

		/**
		 * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
		 */
		private String keyPath;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
