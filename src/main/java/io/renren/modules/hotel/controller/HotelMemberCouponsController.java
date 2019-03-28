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
import io.renren.modules.hotel.entity.HotelMemberCouponsEntity;
import io.renren.modules.hotel.service.HotelMemberCouponsService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@RestController
@RequestMapping("hotel/hotelmembercoupons")
public class HotelMemberCouponsController {
	@Autowired
	private HotelMemberCouponsService hotelMemberCouponsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelmembercoupons:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelMemberCouponsService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelmembercoupons:info")
	public R info(@PathVariable("id") Integer id) {
		HotelMemberCouponsEntity hotelMemberCoupons = hotelMemberCouponsService.getById(id);

		return R.ok().put("hotelMemberCoupons", hotelMemberCoupons);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelmembercoupons:save")
	public R save(@RequestBody HotelMemberCouponsEntity hotelMemberCoupons) {
		hotelMemberCouponsService.save(hotelMemberCoupons);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelmembercoupons:update")
	public R update(@RequestBody HotelMemberCouponsEntity hotelMemberCoupons) {
		hotelMemberCouponsService.updateById(hotelMemberCoupons);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelmembercoupons:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelMemberCouponsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
