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
import io.renren.modules.hotel.entity.HotelCzhdEntity;
import io.renren.modules.hotel.service.HotelCzhdService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:40
 */
@RestController
@RequestMapping("hotel/hotelczhd")
public class HotelCzhdController {
	@Autowired
	private HotelCzhdService hotelCzhdService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelczhd:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelCzhdService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelczhd:info")
	public R info(@PathVariable("id") Integer id) {
		HotelCzhdEntity hotelCzhd = hotelCzhdService.getById(id);

		return R.ok().put("hotelCzhd", hotelCzhd);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelczhd:save")
	public R save(@RequestBody HotelCzhdEntity hotelCzhd) {
		hotelCzhdService.save(hotelCzhd);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelczhd:update")
	public R update(@RequestBody HotelCzhdEntity hotelCzhd) {
		hotelCzhdService.updateById(hotelCzhd);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelczhd:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelCzhdService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
