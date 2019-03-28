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
import io.renren.modules.hotel.entity.HotelScoreEntity;
import io.renren.modules.hotel.service.HotelScoreService;

/**
 * 积分明细表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:32
 */
@RestController
@RequestMapping("hotel/hotelscore")
public class HotelScoreController {
	@Autowired
	private HotelScoreService hotelScoreService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelscore:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelScoreService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelscore:info")
	public R info(@PathVariable("id") Integer id) {
		HotelScoreEntity hotelScore = hotelScoreService.getById(id);

		return R.ok().put("hotelScore", hotelScore);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelscore:save")
	public R save(@RequestBody HotelScoreEntity hotelScore) {
		hotelScoreService.save(hotelScore);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelscore:update")
	public R update(@RequestBody HotelScoreEntity hotelScore) {
		hotelScoreService.updateById(hotelScore);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelscore:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelScoreService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
