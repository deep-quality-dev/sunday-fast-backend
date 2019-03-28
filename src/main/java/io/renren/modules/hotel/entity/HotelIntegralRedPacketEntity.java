package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 积分红包表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@Data
@TableName("t_hotel_integral_red_packet")
public class HotelIntegralRedPacketEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 商品id
	 */
	private Integer goodsId;
	/**
	 * 红包金额
	 */
	private BigDecimal money;
	/**
	 * 1新增,2使用
	 */
	private Integer state;
	/**
	 * 时间
	 */
	private Integer time;
	/**
	 * 商家ID
	 */
	private Integer sellerId;

}
