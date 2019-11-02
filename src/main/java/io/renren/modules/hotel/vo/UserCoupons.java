package io.renren.modules.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserCoupons {
	private Long id;

	/**
	 * 优惠券类型
	 */
	private int type;

	/**
	 * 优惠券名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
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

	/**
	 * 商家ID
	 */
	private Long sellerId;
	
	private String sellerName;
	
	private String rule;
}
