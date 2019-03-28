package io.renren.modules.hotel.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.hotel.service.HotelMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店公共接口", tags = { "酒店公共接口" })
@RestController
@RequestMapping("/{appId}/hotel/common")
public class CommonAPI {

	@Autowired
	private HotelMemberService hotelMemberService;
	
	
	@ApiOperation("发送验证码")
	@GetMapping("/sendSmsCode")
	public R sendSmsCode(@PathVariable String appId, String mobile) {
		hotelMemberService.sendSmsCode(mobile);
		return R.ok();
	}
}
