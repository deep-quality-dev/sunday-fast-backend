package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 佣金收益表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@Data
@TableName("t_hotel_earnings")
public class HotelEarningsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 订单ID
	 */
	private Integer orderId;
	/**
	 * 用户
	 */
	private Integer userId;
	/**
	 * 下线
	 */
	private Integer sonId;
	/**
	 * 金额
	 */
	private BigDecimal money;
	/**
	 * 时间
	 */
	private Integer time;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 佣金状态,1冻结,2有效,3无效
	 */
	private Integer state;
	/**
	 * 商家ID
	 */
	private Integer sellerId;

}
