package io.renren.modules.hotel.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 会员卡列表
 * @author taoz
 *
 */
@Data
public class VipCardItemVo {

	private Integer id;

	private String name;

	private String conetnt;

	private String discount;

	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;

	/**
	 * 是否需要支付
	 */
	private int payFlag;
}
