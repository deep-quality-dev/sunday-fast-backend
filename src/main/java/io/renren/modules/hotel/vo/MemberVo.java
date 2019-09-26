package io.renren.modules.hotel.vo;

import lombok.Data;

/**
 * 用户信息
 * 
 * @author taoz
 *
 */
@Data
public class MemberVo {

	private String headImgUrl;

	private String nickName;

	private String level;

	private String mobile;

	private String gender;

	private String birthday;

	// 1认证，未认证
	private int authFlag = 0;

	private int payPwdFlag = 0;

	private String identityNo;

	private String zsName;

}
