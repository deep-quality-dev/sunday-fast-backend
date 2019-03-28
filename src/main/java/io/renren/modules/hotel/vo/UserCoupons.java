package io.renren.modules.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class UserCoupons {
	private Long id;

	/**
	 * 优惠券名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 优惠条件
	 */
	private String conditions;
	/**
	 * 发布数量
	 */
	private Integer number;
	/**
	 * 金额
	 */
	private BigDecimal cost;
}
