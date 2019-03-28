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
import io.renren.modules.hotel.entity.HotelRechargeEntity;
import io.renren.modules.hotel.service.HotelRechargeService;

/**
 * 充值表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@RestController
@RequestMapping("hotel/hotelrecharge")
public class HotelRechargeController {
	@Autowired
	private HotelRechargeService hotelRechargeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelrecharge:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelRechargeService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelrecharge:info")
	public R info(@PathVariable("id") Integer id) {
		HotelRechargeEntity hotelRecharge = hotelRechargeService.getById(id);

		return R.ok().put("hotelRecharge", hotelRecharge);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelrecharge:save")
	public R save(@RequestBody HotelRechargeEntity hotelRecharge) {
		hotelRechargeService.save(hotelRecharge);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelrecharge:update")
	public R update(@RequestBody HotelRechargeEntity hotelRecharge) {
		hotelRechargeService.updateById(hotelRecharge);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelrecharge:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelRechargeService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
