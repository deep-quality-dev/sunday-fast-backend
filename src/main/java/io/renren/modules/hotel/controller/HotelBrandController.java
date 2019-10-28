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

import cn.hutool.core.date.DateUtil;
import io.renren.modules.hotel.entity.HotelBrandEntity;
import io.renren.modules.hotel.service.HotelBrandService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-16 19:47:22
 */
@RestController
@RequestMapping("hotel/hotelbrand")
public class HotelBrandController {
	@Autowired
	private HotelBrandService hotelBrandService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelbrand:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelBrandService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelbrand:info")
	public R info(@PathVariable("id") Long id) {
		HotelBrandEntity hotelBrand = hotelBrandService.getById(id);

		return R.ok().put("hotelBrand", hotelBrand);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelbrand:save")
	public R save(@RequestBody HotelBrandEntity hotelBrand) {
		hotelBrand.setCreatTime(DateUtil.date());
		hotelBrandService.save(hotelBrand);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelbrand:update")
	public R update(@RequestBody HotelBrandEntity hotelBrand) {
		hotelBrandService.updateById(hotelBrand);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelbrand:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelBrandService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
