package io.renren.modules.hotel.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.service.HotelWxConfigService;
import lombok.Data;

/**
 * 初始化微信配置
 * @author taoz
 *
 */
@Data
//@ConfigurationProperties(prefix = "wx.mp")
@Component
public class WxMpProperties {
	
	@Autowired
	private HotelWxConfigService hotelWxConfigService;
	
	private List<MpConfig> configs;

	@PostConstruct // 指定该方法在对象被创建后马上调用 相当于配置文件中的init-method属性
	public void init() {
		MpConfig config = null;
		List<MpConfig> configs = new ArrayList<WxMpProperties.MpConfig>();
		List<HotelWxConfigEntity> configEntities = hotelWxConfigService.list();
		for (HotelWxConfigEntity hotelWxConfigEntity : configEntities) {
			config = new MpConfig();
			config.setAppId(hotelWxConfigEntity.getAppId());
			config.setSecret(hotelWxConfigEntity.getSecret());
			config.setAesKey(hotelWxConfigEntity.getAesKey());
			config.setToken(hotelWxConfigEntity.getToken());
			configs.add(config);
		}
		this.setConfigs(configs);
	}

	@Data
	public class MpConfig {
		/**
		 * 设置微信公众号的appid
		 */
		private String appId;

		/**
		 * 设置微信公众号的app secret
		 */
		private String secret;

		/**
		 * 设置微信公众号的token
		 */
		private String token;

		/**
		 * 设置微信公众号的EncodingAESKey
		 */
		private String aesKey;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
