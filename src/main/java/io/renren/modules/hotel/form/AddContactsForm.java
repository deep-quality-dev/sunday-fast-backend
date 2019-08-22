package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加联系人
 * 
 * @author taoz
 *
 */
@Data
@ApiModel(value = "添加联系人表单")
public class AddContactsForm {

	@ApiModelProperty(value = "id-添加为空")
	private Long id;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String name;
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机")
	@NotBlank(message = "手机不能为空")
	private String mobile;
}
