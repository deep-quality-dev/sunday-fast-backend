package io.renren.modules.hotel.form;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 提现申请表单
 * 
 * @author taoz
 *
 */
@Data
public class WithdrawalApplyForm {

	private BigDecimal amount;

	private int accountType;

	private String account;

	private String realName;

}
