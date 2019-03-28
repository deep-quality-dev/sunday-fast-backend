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
import io.renren.modules.hotel.entity.HotelDistributionEntity;
import io.renren.modules.hotel.service.HotelDistributionService;

/**
 * 分销申请
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@RestController
@RequestMapping("hotel/hoteldistribution")
public class HotelDistributionController {
	@Autowired
	private HotelDistributionService hotelDistributionService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hoteldistribution:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelDistributionService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hoteldistribution:info")
	public R info(@PathVariable("id") Integer id) {
		HotelDistributionEntity hotelDistribution = hotelDistributionService.getById(id);

		return R.ok().put("hotelDistribution", hotelDistribution);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hoteldistribution:save")
	public R save(@RequestBody HotelDistributionEntity hotelDistribution) {
		hotelDistributionService.save(hotelDistribution);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hoteldistribution:update")
	public R update(@RequestBody HotelDistributionEntity hotelDistribution) {
		hotelDistributionService.updateById(hotelDistribution);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hoteldistribution:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelDistributionService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
