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
import io.renren.modules.hotel.entity.HotelDistributionSetEntity;
import io.renren.modules.hotel.service.HotelDistributionSetService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@RestController
@RequestMapping("hotel/hoteldistributionset")
public class HotelDistributionSetController {
	@Autowired
	private HotelDistributionSetService hotelDistributionSetService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hoteldistributionset:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelDistributionSetService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hoteldistributionset:info")
	public R info(@PathVariable("id") Integer id) {
		HotelDistributionSetEntity hotelDistributionSet = hotelDistributionSetService.getById(id);

		return R.ok().put("hotelDistributionSet", hotelDistributionSet);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hoteldistributionset:save")
	public R save(@RequestBody HotelDistributionSetEntity hotelDistributionSet) {
		hotelDistributionSetService.save(hotelDistributionSet);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hoteldistributionset:update")
	public R update(@RequestBody HotelDistributionSetEntity hotelDistributionSet) {
		hotelDistributionSetService.updateById(hotelDistributionSet);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hoteldistributionset:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelDistributionSetService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
