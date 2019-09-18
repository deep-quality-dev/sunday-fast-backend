package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 注册会员表单
 * 
 * @author taoz
 *
 */
@Data
@ApiModel(value = "注册会员卡表单")
public class BecomeVipForm {
	/**
	 * 等级类型
	 */
	@ApiModelProperty(value = "等级类型")
	@NotBlank(message = "等级类型不能为空")
	private Long levelId;
	/**
	 * 证件类型
	 */
	@ApiModelProperty(value = "证件类型 1-身份证 先这样传")
	@NotBlank(message = "证件类型不能为空")
	private Integer certificate;
	/**
	 * 证件号
	 */
	@ApiModelProperty(value = "证件")
	@NotBlank(message = "证件不能为空")
	private String certificateNo;
	/**
	 * 名字
	 */
	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String name;

	/**
	 * 推销员
	 */
	@ApiModelProperty(value = "推销员")
	private String salesman;

	/**
	 * 小程序场景
	 */
	@ApiModelProperty(value = "小程序formId")
	private String formId;
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机")
	@NotBlank(message = "手机不能为空")
	private String mobile;
//	/**
//	 * 性别
//	 */
//	@ApiModelProperty(value = "性别")
//	private Integer gender;
//	/**
//	 * 联系地址
//	 */
//	@ApiModelProperty(value = "联系地址")
//	private String address;
//
//	@ApiModelProperty(value = "商家ID")
//	@NotBlank(message = "商家不能为空")
//	private Long sellerId;
	
	@ApiModelProperty(value = "支付方式")
	private String payMethod;
}
