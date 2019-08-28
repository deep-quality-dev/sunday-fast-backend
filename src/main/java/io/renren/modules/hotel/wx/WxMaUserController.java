package io.renren.modules.hotel.wx;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import io.renren.common.utils.R;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.hotel.config.WxMaConfiguration;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.service.HotelMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信小程序用户接口
 */
@Api(value = "微信小程序用户接口", tags = { "微信小程序用户接口" })
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * 登陆接口
	 */
	@ApiOperation("登陆接口")
	@GetMapping("/login")
	public R login(@PathVariable String appid, String code, String signature, String rawData, String encryptedData, String iv) {
		if (StringUtils.isBlank(code)) {
			return R.error("empty jscode");
		}
		final WxMaService wxService = WxMaConfiguration.getMaService(appid);
		try {
			WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
			this.logger.info(session.getSessionKey());
			this.logger.info(session.getOpenid());
			// 获取用户信息
			// 用户信息校验
			if (!wxService.getUserService().checkUserInfo(session.getSessionKey(), rawData, signature)) {
				return R.error("user check failed");
			}
			// 解密用户信息
			WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(session.getSessionKey(), encryptedData, iv);
			HotelMemberEntity member = hotelMemberService.wxMaLogin(userInfo);
			String token = jwtUtils.generateToken(member.getId());
			Map<String, Object> map = new HashMap<>();
			map.put("token", token);
			map.put("bindMobild", StrUtil.isNotEmpty(member.getTel()) ? 1 : 0);
			map.put("sessionKey", session.getSessionKey());
			map.put("expire", jwtUtils.getExpire());
			return R.ok(map);
		} catch (WxErrorException e) {
			this.logger.error(e.getMessage(), e);
			return R.error();
		}
	}

	/**
	 * <pre>
	 * 获取用户信息接口
	 * </pre>
	 */
	@ApiOperation("获取用户信息接口")
	@GetMapping("/info")
	public R info(@PathVariable String appid, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
		final WxMaService wxService = WxMaConfiguration.getMaService(appid);

		// 用户信息校验
		if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			return R.error("user check failed");
		}

		// 解密用户信息
		WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
		return R.ok(userInfo);
	}

	/**
	 * <pre>
	 * 获取用户绑定手机号信息
	 * </pre>
	 */
	@ApiOperation("获取用户绑定手机号信息")
	@GetMapping("/phone")
	public R phone(@PathVariable String appid, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
		final WxMaService wxService = WxMaConfiguration.getMaService(appid);

		// 用户信息校验
		if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			return R.ok("user check failed");
		}
		WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
		// 解密
		WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//		hotelMemberService.bindWxPhone(userInfo.getOpenId(), phoneNoInfo);
		return R.ok(phoneNoInfo);
	}

}