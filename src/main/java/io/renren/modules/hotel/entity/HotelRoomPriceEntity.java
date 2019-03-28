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
@TableName("t_hotel_room_price")
public class HotelRoomPriceEntity implements Serializable {
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
	private Long moneyId;
	/**
	 * 当天日期 时间戳格式
	 */
	private Integer roomdate;
	/**
	 * 当天日期 日期格式
	 */
	private String thisdate;
	/**
	 * 原价
	 */
	private BigDecimal oprice;
	/**
	 * 现价
	 */
	private BigDecimal cprice;
	/**
	 * 会员价
	 */
	private BigDecimal mprice;
	/**
	 * 
	 */
	private BigDecimal frontMoney;
	/**
	 * 
	 */
	private String num;
	/**
	 * 
	 */
	private Integer status;

}
