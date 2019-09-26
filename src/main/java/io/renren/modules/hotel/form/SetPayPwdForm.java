package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SetPayPwdForm {

	@ApiModelProperty(value = "密码")
	@NotBlank(message = "密码不能为空")
	private String pwd;

	@ApiModelProperty(value = "手机号")
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@ApiModelProperty(value = "短信验证码")
	@NotBlank(message = "短信验证码不能为空")
	private String vcode;
}
