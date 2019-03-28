package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-21 22:44:37
 */
@Data
@TableName("t_hotel_room_money")
public class HotelRoomMoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private Long roomId;
	/**
	 * 
	 */
	private String motitle;
	/**
	 * 原价
	 */
	private BigDecimal oprice;
	/**
	 * 优惠价
	 */
	private BigDecimal cprice;
	/**
	 * 会员价
	 */
	private BigDecimal mprice;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 是否热门推荐
	 */
	private Integer isHot;

}
