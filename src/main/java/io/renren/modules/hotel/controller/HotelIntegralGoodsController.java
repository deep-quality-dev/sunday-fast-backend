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
import io.renren.modules.hotel.entity.HotelIntegralGoodsEntity;
import io.renren.modules.hotel.service.HotelIntegralGoodsService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@RestController
@RequestMapping("hotel/hotelintegralgoods")
public class HotelIntegralGoodsController {
	@Autowired
	private HotelIntegralGoodsService hotelIntegralGoodsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelintegralgoods:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelIntegralGoodsService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelintegralgoods:info")
	public R info(@PathVariable("id") Integer id) {
		HotelIntegralGoodsEntity hotelIntegralGoods = hotelIntegralGoodsService.getById(id);

		return R.ok().put("hotelIntegralGoods", hotelIntegralGoods);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelintegralgoods:save")
	public R save(@RequestBody HotelIntegralGoodsEntity hotelIntegralGoods) {
		hotelIntegralGoodsService.save(hotelIntegralGoods);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelintegralgoods:update")
	public R update(@RequestBody HotelIntegralGoodsEntity hotelIntegralGoods) {
		hotelIntegralGoodsService.updateById(hotelIntegralGoods);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelintegralgoods:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelIntegralGoodsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
