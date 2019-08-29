package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 充值表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@Data
@TableName("t_hotel_recharge")
public class HotelRechargeEntity implements Serializable {
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
	 * 充值金额
	 */
	private BigDecimal czMoney;
	/**
	 * 赠送金额
	 */
	private BigDecimal zsMoney;
	/**
	 * 备注信息
	 */
	private String note;
	/**
	 * 商户号
	 */
	private String outTradeNo;
	/**
	 * 1未付款,2已付款
	 */
	private Integer state;
	/**
	 * 
	 */
	private Integer time;
	/**
	 * 
	 */
	private Long cardId;

}
