package io.renren.modules.hotel.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelProtocolEntity;
import io.renren.modules.hotel.service.HotelProtocolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店平台接口", tags = { "酒店平台接口" })
@RestController
@RequestMapping("/hotel/platfotm")
public class HotelPlatformAPI {

	@Autowired
	private HotelProtocolService hotelProtocolService;

	@ApiOperation("获取平台协议")
	@GetMapping("/protocol")
	public R protocol(String code) {
		HotelProtocolEntity hotelProtocolEntity = hotelProtocolService.getOne(Wrappers.<HotelProtocolEntity>lambdaQuery().eq(HotelProtocolEntity::getCode, code));
		return R.ok(hotelProtocolEntity);
	}
}
