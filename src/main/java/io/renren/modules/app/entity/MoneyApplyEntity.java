package io.renren.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_money_apply")
public class MoneyApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer uid;
	/**
	 * 提现金额
	 */
	private BigDecimal money;
	/**
	 * 支付宝账号
	 */
	private String alipayAccount;
	/**
	 * 支付宝姓名
	 */
	private String alipayName;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 审核状态 0审核中 1已打款
	 */
	private Integer state;

}
