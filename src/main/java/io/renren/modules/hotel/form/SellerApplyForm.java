package io.renren.modules.hotel.form;

import lombok.Data;

/**
 * 商家入驻申请表单
 * 
 * @author taoz
 *
 */
@Data
public class SellerApplyForm {

	private String name;
	/**
	 * 星级
	 */
	private String star;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系人
	 */
	private String linkName;
	/**
	 * 联系电话
	 */
	private String linkTel;
	/**
	 * 酒店电话
	 */
	private String tel;

	private String coordinates;

	/**
	 * 身份证正面照
	 */
	private String sfzImg1;
	/**
	 * 身份证反面照
	 */
	private String sfzImg2;
	/**
	 * 营业执照
	 */
	private String yyImg;

}
