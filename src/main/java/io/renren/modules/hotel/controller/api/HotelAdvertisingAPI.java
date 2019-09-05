package io.renren.modules.hotel.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelAdvertisingEntity;
import io.renren.modules.hotel.service.HotelAdvertisingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店广告接口", tags = { "酒店广告接口" })
@RestController
@RequestMapping("/hotel/adv")
public class HotelAdvertisingAPI extends BaseController {

	@Autowired
	private HotelAdvertisingService hotelAdvertisingService;

	/**
	 * 获取广告
	 * 
	 * @param appId
	 * @param type
	 * @return
	 */
	@ApiOperation("获取广告")
	@GetMapping("/loadByType")
	public R loadByType(@RequestParam int type) {
		List<HotelAdvertisingEntity> advertisingEntities = hotelAdvertisingService.loadByType(type);
		return R.ok().put("data", advertisingEntities);
	}
}
