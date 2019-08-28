package io.renren.modules.hotel.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 卡片充值
 * 
 * @author taoz
 *
 */
@Data
@ApiModel(value = "卡片充值表单")
public class CardRechargeForm {

	@ApiModelProperty(value = "卡片ID")
	@NotBlank(message = "卡片ID不能为空")
	private Long cardId;

	@ApiModelProperty(value = "充值金额item ID，选择其他金额是传 -1")
	private Long rechargeConfigId;

	@ApiModelProperty(value = "充值金额，当选择其他金额的时候传该参数")
	private BigDecimal amount;
}
