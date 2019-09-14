package io.renren.modules.hotel.wx;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.StrUtil;
import io.renren.common.utils.R;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.hotel.config.WxMpConfiguration;
import io.renren.modules.hotel.controller.api.BaseController;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.service.HotelMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Edward
 */
@Slf4j
@Api(value = "微信认证接口", tags = { "微信认证接口" })
@RestController
@RequestMapping("/{appId}/wx/auth")
public class WxRedirectController extends BaseController {

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * 微信code 登陆
	 * 
	 * @param appId
	 * @param code
	 * @param map
	 * @return
	 */
	@ApiOperation("微信code登陆")
	@RequestMapping("/login")
	public R greetUser(@PathVariable String appId, @RequestParam String code, ModelMap map) {
		WxMpService mpService = WxMpConfiguration.getMpServices().get(appId);
		WxMpUser user = null;
		HotelMemberEntity hotelMemberEntity = null;
		try {
			WxMpOAuth2AccessToken accessToken = mpService.oauth2getAccessToken(code);
			user = mpService.oauth2getUserInfo(accessToken, null);
			hotelMemberEntity = hotelMemberService.wxLogin(user, sellerId(appId));
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		// 生成token
		String token = jwtUtils.generateToken(hotelMemberEntity.getId());
		Map<String, Object> result = new HashMap<>();
		result.put("token", token);
		if (StrUtil.isNotBlank(hotelMemberEntity.getTel())) {
			result.put("mobile", 1);
		}
		result.put("expire", jwtUtils.getExpire());
		return R.ok().put("data", result);
	}

	/**
	 * 获取js票据
	 * 
	 * @param url
	 * @return
	 * @throws WxErrorException
	 */
	@ApiOperation("获取js票据")
	@PostMapping("/getWeJsTicket")
	public R getWeJsTicket(@PathVariable String appId, @RequestBody Map<String, String> params) throws WxErrorException {
		log.info(params.get("url"));
		WxMpService mpService = WxMpConfiguration.getMpServices().get(appId);
		WxJsapiSignature jsapiSignature = mpService.createJsapiSignature(params.get("url"));
		return R.ok().put("data", jsapiSignature);
	}
}
