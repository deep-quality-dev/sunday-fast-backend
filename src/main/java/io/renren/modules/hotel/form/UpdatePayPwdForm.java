package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePayPwdForm {

	@ApiModelProperty(value = "旧密码")
	@NotBlank(message = "旧密码不能为空")
	private String oldPwd;

	@ApiModelProperty(value = "新密码")
	@NotBlank(message = "新密码不能为空")
	private String newPwd;

}
