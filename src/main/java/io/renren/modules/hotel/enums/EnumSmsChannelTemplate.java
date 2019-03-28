package io.renren.modules.hotel.enums;

import lombok.Getter;
import lombok.Setter;

public enum EnumSmsChannelTemplate {

	/**
	 * 登录验证
	 */
	LOGIN_NAME_LOGIN("loginCodeChannel", "金誉兴电子"),;

	/**
	 * 模板名称
	 */
	@Getter
	@Setter
	private String template;
	/**
	 * 模板签名
	 */
	@Getter
	@Setter
	private String signName;

	EnumSmsChannelTemplate(String template, String signName) {
		this.template = template;
		this.signName = signName;
	}
}
