package io.renren.modules.hotel.controller.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.vo.UserCoupons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店优惠券接口", tags = { "酒店优惠券接口" })
@RestController
@RequestMapping("/hotel/coupons")
public class HotelCouponAPI extends BaseController {

	@Autowired
	private HotelCouponsService hotelCouponsService;

	/**
	 * 商家优惠券
	 * 
	 * @param appId  app ID
	 * @param userId 用户ID
	 * @return
	 */
	@Login
	@ApiOperation("商家优惠券")
	@GetMapping("/sellerCoupons")
	public R sellerCoupons(@PathVariable String appId, @RequestAttribute("userId") Long userId, @RequestParam Map<String, Object> params) {
		PageUtils page = hotelCouponsService.sellerCoupons(sellerId(appId), userId, params);
		return R.ok().put("data", page);
	}

	@Login
	@ApiOperation("商家可用优惠券")
	@GetMapping("/sellerCanUseCoupons")
	public R canUseCoupons(@RequestAttribute("userId") Long userId, @RequestParam(name = "sellerId", required = true) Long sellerId,@RequestParam(name = "amount", required = true)BigDecimal amount) {
		List<UserCoupons> coupons = hotelCouponsService.canUseCoupons(userId, sellerId,amount);
		return R.ok(coupons);
	}
}
