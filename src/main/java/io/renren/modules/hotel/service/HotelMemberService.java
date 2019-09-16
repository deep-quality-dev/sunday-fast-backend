package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.vo.MemberVo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
public interface HotelMemberService extends IService<HotelMemberEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 用户信息
	 * 
	 * @param sellerId
	 * @return
	 */
	MemberVo userInfo(Long sellerId);

	/**
	 * 微信登陆
	 * 
	 * @param user
	 * @param sellerId
	 * @return
	 */
	HotelMemberEntity wxLogin(WxMpUser user, Long sellerId);

	/**
	 * 更新用户积分
	 * 
	 * @param userId 用户ID
	 * @param score  积分
	 */
	void updateUserScore(Long userId, String score);

	/**
	 * 发送验证码
	 * 
	 * @param mobile
	 */
	void sendSmsCode(String mobile);

	/**
	 * 绑定手机
	 * 
	 * @param sellerId 商家
	 * @param userId   用户
	 * @param mobile   手机
	 * @param vcode    验证码
	 * @return
	 */
	void bindSms(Long sellerId, Long userId, String mobile, String vcode);

	/**
	 * 微信小程序登陆
	 * 
	 * @param userInfo
	 * @return
	 */
	HotelMemberEntity wxMaLogin(WxMaUserInfo userInfo);

	/**
	 * 绑定微信手机
	 * @param openId 
	 * @param phoneNoInfo
	 */
	void bindWxPhone(String openId, WxMaPhoneNumberInfo phoneNoInfo);

}
