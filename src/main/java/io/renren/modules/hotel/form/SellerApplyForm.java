package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商家入驻申请表单
 * 
 * @author taoz
 *
 */
@Data
public class SellerApplyForm {

	
	@ApiModelProperty(value = "酒店名字")
	@NotBlank(message = "酒店名字不能为空")
	private String name;
	/**
	 * 星级
	 */
	@ApiModelProperty(value = "酒店星级")
	@NotBlank(message = "酒店星级不能为空")
	private String star;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "酒店地址")
	@NotBlank(message = "酒店地址不能为空")
	private String address;
	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "酒店联系人")
	@NotBlank(message = "酒店联系人不能为空")
	private String linkName;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "酒店负责人")
	@NotBlank(message = "酒店负责人电话不能为空")
	private String linkTel;
	/**
	 * 酒店电话
	 */
	@ApiModelProperty(value = "酒店电话")
	@NotBlank(message = "酒店电话不能为空")
	private String tel;

	
	@ApiModelProperty(value = "酒店经纬度坐标")
	@NotBlank(message = "酒店经纬度不能为空")
	private String coordinates;

	/**
	 * 身份证正面照
	 */
	@ApiModelProperty(value = "身份证正面照片")
	@NotBlank(message = "身份证正面照不能为空")
	private String sfzImg1;
	/**
	 * 身份证反面照
	 */
	@ApiModelProperty(value = "身份证反面照片")
	@NotBlank(message = "身份证反面照不能为空")
	private String sfzImg2;
	/**
	 * 营业执照
	 */
	@ApiModelProperty(value = "营业执照照片")
	@NotBlank(message = "营业执照不能为空")
	private String yyImg;

	/**
	 * 品牌ID
	 */
	@ApiModelProperty(value = "品牌ID")
	@NotBlank(message = "品牌ID不能为空")
	private String brandId;
	
	/**
	 * 酒店介绍
	 */
	private String introduction;

}
