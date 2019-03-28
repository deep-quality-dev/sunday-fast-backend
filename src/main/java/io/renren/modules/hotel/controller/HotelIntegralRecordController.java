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
import io.renren.modules.hotel.entity.HotelIntegralRecordEntity;
import io.renren.modules.hotel.service.HotelIntegralRecordService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@RestController
@RequestMapping("hotel/hotelintegralrecord")
public class HotelIntegralRecordController {
	@Autowired
	private HotelIntegralRecordService hotelIntegralRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelintegralrecord:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelIntegralRecordService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelintegralrecord:info")
	public R info(@PathVariable("id") Integer id) {
		HotelIntegralRecordEntity hotelIntegralRecord = hotelIntegralRecordService.getById(id);

		return R.ok().put("hotelIntegralRecord", hotelIntegralRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelintegralrecord:save")
	public R save(@RequestBody HotelIntegralRecordEntity hotelIntegralRecord) {
		hotelIntegralRecordService.save(hotelIntegralRecord);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelintegralrecord:update")
	public R update(@RequestBody HotelIntegralRecordEntity hotelIntegralRecord) {
		hotelIntegralRecordService.updateById(hotelIntegralRecord);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelintegralrecord:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelIntegralRecordService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
