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
import io.renren.modules.hotel.entity.HotelEarningsEntity;
import io.renren.modules.hotel.service.HotelEarningsService;

/**
 * 佣金收益表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@RestController
@RequestMapping("hotel/hotelearnings")
public class HotelEarningsController {
	@Autowired
	private HotelEarningsService hotelEarningsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelearnings:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelEarningsService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelearnings:info")
	public R info(@PathVariable("id") Integer id) {
		HotelEarningsEntity hotelEarnings = hotelEarningsService.getById(id);

		return R.ok().put("hotelEarnings", hotelEarnings);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelearnings:save")
	public R save(@RequestBody HotelEarningsEntity hotelEarnings) {
		hotelEarningsService.save(hotelEarnings);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelearnings:update")
	public R update(@RequestBody HotelEarningsEntity hotelEarnings) {
		hotelEarningsService.updateById(hotelEarnings);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelearnings:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelEarningsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
