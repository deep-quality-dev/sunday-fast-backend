package io.renren.modules.hotel.enums;

import lombok.Getter;
import lombok.Setter;

public enum EnumSmsChannelTemplate {

	/**
	 * 登录验证
	 */
	LOGIN_NAME_LOGIN("loginCodeChannel", "金誉兴电子"), VALIDATE_CODE("validateCode", "千兰会"), //
	SELLER_AUDIT_PASS_CHANNEL("sellerAuditPassChannel", "千兰会"), //
	SELLERAUDITREFUSE_CHANNEL("sellerAuditRefuseChannel", "千兰会"), //
	SELLER_RESERVE_SUCCESS_CHANNEL("sellerReserveSuccessChannel", "千兰会"), //
	SELLER_RESERVE_CANCEL_CHANNEL("sellerReserveCancelCHannel", "千兰会"),//
	;

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
