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
import io.renren.modules.hotel.entity.HotelDistributionUserEntity;
import io.renren.modules.hotel.service.HotelDistributionUserService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@RestController
@RequestMapping("hotel/hoteldistributionuser")
public class HotelDistributionUserController {
	@Autowired
	private HotelDistributionUserService hotelDistributionUserService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hoteldistributionuser:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelDistributionUserService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hoteldistributionuser:info")
	public R info(@PathVariable("id") Integer id) {
		HotelDistributionUserEntity hotelDistributionUser = hotelDistributionUserService.getById(id);

		return R.ok().put("hotelDistributionUser", hotelDistributionUser);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hoteldistributionuser:save")
	public R save(@RequestBody HotelDistributionUserEntity hotelDistributionUser) {
		hotelDistributionUserService.save(hotelDistributionUser);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hoteldistributionuser:update")
	public R update(@RequestBody HotelDistributionUserEntity hotelDistributionUser) {
		hotelDistributionUserService.updateById(hotelDistributionUser);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hoteldistributionuser:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelDistributionUserService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
