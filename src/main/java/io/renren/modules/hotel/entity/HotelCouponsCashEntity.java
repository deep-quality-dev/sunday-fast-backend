package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 代金券
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 22:22:26
 */
@Data
@TableName("t_hotel_coupons_cash")
public class HotelCouponsCashEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 门店ID
	 */
	private Long sellerId;
	/**
	 * 优惠券名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 优惠条件
	 */
	private BigDecimal conditions;
	/**
	 * 发布数量
	 */
	private Integer number;
	/**
	 * 金额
	 */
	private BigDecimal cost;
	/**
	 * 发布类型1,平台,2门店
	 */
	private Integer type;
	/**
	 * 说明
	 */
	private String introduce;
	/**
	 * 领取数量
	 */
	private Integer lqNum;
	/**
	 * 每人可领取张数
	 */
	private Integer klqzs;
	/**
	 * 
	 */
	private Integer time;
	
	private String ruleDec;

}
