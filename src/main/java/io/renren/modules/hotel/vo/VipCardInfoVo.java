package io.renren.modules.hotel.vo;

import lombok.Data;

/**
 * 会员卡信息
 * 
 * @author taoz
 *
 */
@Data
public class VipCardInfoVo {

	private String validityDate;

	private String icon;

	private String name;

	private String userName;

	private String mobile;

	private String certificateNo;
}
