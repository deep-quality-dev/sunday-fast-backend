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
import io.renren.modules.hotel.entity.HotelNavEntity;
import io.renren.modules.hotel.service.HotelNavService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:34
 */
@RestController
@RequestMapping("hotel/hotelnav")
public class HotelNavController {
	@Autowired
	private HotelNavService hotelNavService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelnav:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelNavService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelnav:info")
	public R info(@PathVariable("id") Integer id) {
		HotelNavEntity hotelNav = hotelNavService.getById(id);

		return R.ok().put("hotelNav", hotelNav);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelnav:save")
	public R save(@RequestBody HotelNavEntity hotelNav) {
		hotelNavService.save(hotelNav);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelnav:update")
	public R update(@RequestBody HotelNavEntity hotelNav) {
		hotelNavService.updateById(hotelNav);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelnav:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelNavService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
