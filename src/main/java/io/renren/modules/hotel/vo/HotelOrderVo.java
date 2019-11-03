package io.renren.modules.hotel.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	/**
	 * 到店时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date arrivalTime;
	/**
	 * 离店时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date departureTime;

	private String ddTime;

	private String remark;

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
	private Integer classify;
	private Integer status;
	private String totalCost;
	private Integer days;
	private Integer num;

	// 用户余额
	private BigDecimal memberBalance;

	// 用户积分
	private BigDecimal memberIntegral;

	// 支付积分
	private BigDecimal payIntegral;

	/**
	 * 早餐券
	 */
	private String breakCouponName;

	/**
	 * 免房券
	 */
	private String freeRoomCouponName;

	/**
	 * 订单是否是需要预付
	 */
	private int prepay;

	List<OrderDetail> record = new ArrayList<OrderDetail>();
}
