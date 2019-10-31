package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 提现记录
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 14:42:34
 */
@Data
@TableName("t_hotel_withdrawal")
public class HotelWithdrawalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 账号
	 */
	private String username;
	/**
	 * 1支付宝 2.微信 3.银行
	 */
	private Integer type;
	/**
	 * 申请时间
	 */
	private String time;
	/**
	 * 审核时间
	 */
	private String auditTime;
	/**
	 * 1.待审核 2.通过 3.转账中 4.拒绝
	 */
	private Integer state;
	/**
	 * 提现金额
	 */
	private BigDecimal withdrawCost;
	/**
	 * 实际金额
	 */
	private BigDecimal realityCost;

	/**
	 * 提现费率
	 */
	private double rate;
	/**
	 * 商家id
	 */
	private Long sellerId;

	@TableField(exist = false)
	private String sellerName;
	/**
	 * 1显示,0删除
	 */
	private Integer enabled;

	/**
	 * 交易单号
	 */
	private String tradeNo;

}
