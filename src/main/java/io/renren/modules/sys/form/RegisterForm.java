package io.renren.modules.sys.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 注册表单
 * 
 * @author taoz
 *
 */
@Data
public class RegisterForm {

	@ApiModelProperty(value = "手机 ")
	@NotBlank(message = "手机不能为空")
	private String mobile;

	@ApiModelProperty(value = "密码")
	@NotBlank(message = "密码不能为空")
	private String password;

	@ApiModelProperty(value = "图片验证码")
	@NotBlank(message = "图片验证码不能为空")
	private String captcha;

	@ApiModelProperty(value = "短信验证码")
	@NotBlank(message = "短信验证码不能为空")
	private String smsCode;
	
	private String uuid;
}
