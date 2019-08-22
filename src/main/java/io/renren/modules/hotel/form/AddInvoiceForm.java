package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加发票
 * 
 * @author taoz
 *
 */
@Data
@ApiModel(value = "添加发票表单")
public class AddInvoiceForm {

	@ApiModelProperty(value = "id-添加不传")
	private Long id;

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	@NotBlank(message = "公司名称不能为空")
	private String company;
	/**
	 * 纳税人识别号
	 */
	@ApiModelProperty(value = "纳税人识别号")
	@NotBlank(message = "纳税人识别号不能为空")
	private String identifyNumber;
	/**
	 * 开户行
	 */
	@ApiModelProperty(value = "开户行")
	private String openingBank;
	/**
	 * 银行名称
	 */
	@ApiModelProperty(value = "银行名称")
	private String bank;
	/**
	 * 银行地址
	 */
	@ApiModelProperty(value = "银行地址")
	private String bankAddress;
	/**
	 * 注册地址
	 */
	@ApiModelProperty(value = "注册地址")
	private String registAddress;
	/**
	 * 公司电话
	 */
	@ApiModelProperty(value = "公司电话")
	private String companyPhone;
	/**
	 * 发票类型
	 */
	@ApiModelProperty(value = "发票类型")
	private Integer type;

}
