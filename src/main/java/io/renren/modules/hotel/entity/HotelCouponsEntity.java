package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 优惠券
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@Data
@TableName("t_hotel_coupons")
public class HotelCouponsEntity implements Serializable {
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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
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

	@TableField(exist = false)
	private List<Long> roomIds;

}
