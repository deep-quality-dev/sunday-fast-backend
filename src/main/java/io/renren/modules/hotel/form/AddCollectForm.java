package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "添加收藏表单")
public class AddCollectForm {

	/**
	 * 类型，1-酒店
	 */
	@ApiModelProperty(value = "类型，1-酒店")
	@NotBlank(message = "类型不能为空")
	private Integer bizType;
	/**
	 * ID
	 */
	@ApiModelProperty(value = "业务ID，酒店ID")
	@NotBlank(message = "业务ID不能为空")
	private Long bizId;
}
