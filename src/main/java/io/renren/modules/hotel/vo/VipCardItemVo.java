package io.renren.modules.hotel.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 会员卡列表
 * 
 * @author taoz
 *
 */
@Data
public class VipCardItemVo {

	private Integer id;

	private String name;

	private String content;

	private String discount;

	private String sellerName;

	private Long sellerId;

	private String icon;
	private String bgImage;
	private String rgb;

	private String qrCode;
	
	private String cardNo;

	/**
	 * 积分
	 */
	private BigDecimal score;

	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;

	/**
	 * 兑换积分
	 */
	private BigDecimal payIntegral;

	/**
	 * 是否需要支付
	 */
	private int payFlag;
	
	private BigDecimal memberIntegral;
}
