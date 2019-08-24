package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@Data
@TableName("t_hotel_member_coupons")
public class HotelMemberCouponsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 优惠券id
	 */
	private Long couponsId;
	/**
	 * 1领取, 2使用
	 */
	private Integer state;
	/**
	 * 领取时间
	 */
	private Integer time;
	/**
	 * 使用时间
	 */
	private Integer syTime;
	/**
	 * 商家ID
	 */
	private String sellerId;
	
	/**
	 * 优惠券类型 1-优惠券，2-代金券，3-早餐券
	 */
	private int couponsType;

}
