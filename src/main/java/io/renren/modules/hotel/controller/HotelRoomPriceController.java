package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.service.HotelRoomPriceService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-21 22:44:37
 */
@RestController
@RequestMapping("hotel/hotelroomprice")
public class HotelRoomPriceController {
	@Autowired
	private HotelRoomPriceService hotelRoomPriceService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelroomprice:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelRoomPriceService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelroomprice:info")
	public R info(@PathVariable("id") Long id) {
		HotelRoomPriceEntity hotelRoomPrice = hotelRoomPriceService.getById(id);

		return R.ok().put("hotelRoomPrice", hotelRoomPrice);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelroomprice:save")
	public R save(@RequestBody HotelRoomPriceEntity hotelRoomPrice) {
		hotelRoomPriceService.save(hotelRoomPrice);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelroomprice:update")
	public R update(@RequestBody HotelRoomPriceEntity hotelRoomPrice) {
		hotelRoomPriceService.updateById(hotelRoomPrice);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelroomprice:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelRoomPriceService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
