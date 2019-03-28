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
import io.renren.modules.hotel.entity.HotelIntegralClassifyEntity;
import io.renren.modules.hotel.service.HotelIntegralClassifyService;

/**
 * 积分商城分类
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
@RestController
@RequestMapping("hotel/hotelIntegralclassify")
public class HotelIntegralClassifyController {
	@Autowired
	private HotelIntegralClassifyService hotelIntegralClassifyService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotel integralclassify:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelIntegralClassifyService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotel integralclassify:info")
	public R info(@PathVariable("id") Integer id) {
		HotelIntegralClassifyEntity hotelIntegralClassify = hotelIntegralClassifyService.getById(id);

		return R.ok().put("hotelIntegralClassify", hotelIntegralClassify);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotel integralclassify:save")
	public R save(@RequestBody HotelIntegralClassifyEntity hotelIntegralClassify) {
		hotelIntegralClassifyService.save(hotelIntegralClassify);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotel integralclassify:update")
	public R update(@RequestBody HotelIntegralClassifyEntity hotelIntegralClassify) {
		hotelIntegralClassifyService.updateById(hotelIntegralClassify);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotel integralclassify:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelIntegralClassifyService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

}
