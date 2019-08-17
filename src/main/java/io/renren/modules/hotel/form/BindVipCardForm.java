package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "绑定实体会员卡表单")
public class BindVipCardForm {

	@ApiModelProperty(value = "商家ID")
	@NotBlank(message = "商家ID不能为空")
	private Long sellerId;
	/**
	 * 名字
	 */
	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String name;
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机号")
	@NotBlank(message = "手机号不能为空")
	private String mobile;
}
