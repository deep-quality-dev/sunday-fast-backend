package io.renren.modules.hotel.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店支付接口", tags = { "酒店支付接口" })
@RestController
@RequestMapping("/hotel/payment")
public class HotelPaymentAPI extends BaseController {

	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	/**
	 * 支付方式 recharge == type时要过滤调余额支付
	 * @param userId
	 * @param sellerId
	 * @param type
	 * @return
	 */
	@Login
	@GetMapping("/paymentMethod")
	@ApiOperation("支付方式")
	public R paymentMethod(@RequestAttribute("userId") Long userId, @RequestParam(name = "sellerId", required = true) Long sellerId, @RequestParam(name = "type", required = false, defaultValue = "0") String type) {
		List<Map<String, String>> method = new ArrayList<Map<String, String>>();
		Map<String, String> m = new HashMap<String, String>();
		m.put("icon", "");
		m.put("name", "微信支付");
		m.put("code", "wx");
		method.add(m);
		m = new HashMap<String, String>();
		m.put("icon", "");
		m.put("name", "积分支付");
		m.put("code", "integral");
		method.add(m);
		// 判断用户是否开通会员卡
		boolean result = hotelMemberLevelDetailService.hasVipCard(userId, sellerId);
		if (result &&  !"recharge".equals(type)) {
			m = new HashMap<String, String>();
			m.put("icon", "");
			m.put("name", "余额支付");
			m.put("code", "balance");
			method.add(m);
		}
		return R.ok(method);
	}
}
