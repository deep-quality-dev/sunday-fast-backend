package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 佣金提现
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@Data
@TableName("t_hotel_commission_withdrawal")
public class HotelCommissionWithdrawalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 * 1.审核中2.通过3.拒绝
	 */
	private Integer state;
	/**
	 * 申请时间
	 */
	private Integer time;
	/**
	 * 审核时间
	 */
	private Integer shTime;
	/**
	 * 
	 */
	private Integer sellerId;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 提现金额
	 */
	private BigDecimal txCost;
	/**
	 * 实际到账金额
	 */
	private BigDecimal sjCost;
	/**
	 * 
	 */
	private String note;

}
