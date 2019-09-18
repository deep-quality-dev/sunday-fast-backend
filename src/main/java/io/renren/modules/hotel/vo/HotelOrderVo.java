package io.renren.modules.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class HotelOrderVo {

	private Long id;

	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 商家名字
	 */
	private String sellerName;

	private String sellerTel;

	/**
	 * 预定人
	 */
	private String name;
	/**
	 * 联系电话
	 */
	private String tel;

	private Date createTime;
	
	private Long sellerId;
	/**
	 * 商家地址
	 */
	private String sellerAddress;
	/**
	 * 经纬度
	 */
	private String coordinates;

	private Date arrivalTime;
	/**
	 * 离店时间
	 */
	private Date departureTime;

	private int commentFlag;

	/**
	 * 优惠券金额
	 */
	private BigDecimal yhqCost;

	/**
	 * 发票标题
	 */
	private String invoiceTitle;

	// 房型
	private String roomType;
	private Integer status;
	private String totalCost;
	private Integer days;
	private Integer num;
}
