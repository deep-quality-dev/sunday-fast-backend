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
 * @email 18819175397@gmail.com
 * @date 2019-08-26 22:10:48
 */
@Data
@TableName("t_hotel_recharge_config")
public class HotelRechargeConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 充值金额
	 */
	private BigDecimal money;
	/**
	 * 赠送金额
	 */
	private BigDecimal returnMoney;
	/**
	 * 备注
	 */
	private String note;

}
