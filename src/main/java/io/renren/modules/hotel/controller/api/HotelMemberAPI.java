package io.renren.modules.hotel.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.lang.Assert;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.vo.MemberVo;
import io.renren.modules.hotel.vo.UserCoupons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 
 * @author taoz
 *
 */
@Api(value = "酒店会员接口", tags = { "酒店会员接口" })
@RestController
@RequestMapping("/hotel/user")
public class HotelMemberAPI extends BaseController {

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private HotelCouponsService hotelCouponsService;

	@Autowired
	private HotelScoreService hotelScoreService;

	/**
	 * 用户信息
	 * 
	 * @return
	 */
	@Login
	@ApiOperation("用户信息")
	@GetMapping("/userInfo")
	public R userInfo(@RequestAttribute("userId") Long userId) {
		MemberVo memberVo = hotelMemberService.userInfo(userId);
		return R.ok().put("data", memberVo);
	}

	/**
	 * 签到
	 * 
	 * @param userId
	 * @return
	 */
	@Login
	@ApiOperation("用户签到")
	@GetMapping("/signIn")
	public R signIn(@PathVariable String appId, @RequestAttribute("userId") Long userId) {
		boolean result = hotelScoreService.signIn(sellerId(appId), userId);
		return R.ok().put("data", result);
	}


	/**
	 * 用户优惠券
	 * 
	 * @param appId  app ID
	 * @param userId 用户ID
	 * @return
	 */
	@Login
	@ApiOperation("用户优惠券")
	@GetMapping("/userCoupons")
	public R userCoupons(@RequestAttribute("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
		Page<UserCoupons> pageResult = hotelCouponsService.userCoupons(userId, new Page<UserCoupons>(page, limit));
		return R.ok().put("data", pageResult);
	}

	/**
	 * 用户代金券
	 * 
	 * @param appId  app ID
	 * @param userId 用户ID
	 * @return
	 */
	@Login
	@ApiOperation("用户代金券")
	@GetMapping("/userCashCoupons")
	public R userCashCoupons(@RequestAttribute("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
		Page<UserCoupons> pageResult = hotelCouponsService.userCashCoupons(userId, new Page<UserCoupons>(page, limit));
		return R.ok().put("data", pageResult);
	}

	/**
	 * 用户早餐券
	 * 
	 * @param appId  app ID
	 * @param userId 用户ID
	 * @return
	 */
	@Login
	@ApiOperation("用户早餐券")
	@GetMapping("/userBreakfastCoupons")
	public R userBreakfastCoupons(@RequestAttribute("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
		Page<UserCoupons> pageResult = hotelCouponsService.userBreakfastCoupons(userId, new Page<UserCoupons>(page, limit));
		return R.ok().put("data", pageResult);
	}

	@Login
	@ApiOperation("绑定手机")
	@PostMapping("/bindSms")
	public R bindSms(@PathVariable String appId, @RequestAttribute("userId") Long userId, @RequestBody Map<String, String> params) {
		String mobile = params.get("mobile");
		String vcode = params.get("vcode");
		Assert.notBlank(mobile, "手机号不能为空");
		Assert.notBlank(vcode, "验证码不能为空");
		hotelMemberService.bindSms(sellerId(appId), userId, mobile, vcode);
		return R.ok();
	}
}
