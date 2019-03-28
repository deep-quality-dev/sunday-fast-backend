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
import io.renren.modules.hotel.entity.HotelCommissionWithdrawalEntity;
import io.renren.modules.hotel.service.HotelCommissionWithdrawalService;

/**
 * 佣金提现
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@RestController
@RequestMapping("hotel/hotelcommissionwithdrawal")
public class HotelCommissionWithdrawalController {
	@Autowired
	private HotelCommissionWithdrawalService hotelCommissionWithdrawalService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelcommissionwithdrawal:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelCommissionWithdrawalService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelcommissionwithdrawal:info")
	public R info(@PathVariable("id") Integer id) {
		HotelCommissionWithdrawalEntity hotelCommissionWithdrawal = hotelCommissionWithdrawalService.getById(id);

		return R.ok().put("hotelCommissionWithdrawal", hotelCommissionWithdrawal);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelcommissionwithdrawal:save")
	public R save(@RequestBody HotelCommissionWithdrawalEntity hotelCommissionWithdrawal) {
		hotelCommissionWithdrawalService.save(hotelCommissionWithdrawal);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelcommissionwithdrawal:update")
	public R update(@RequestBody HotelCommissionWithdrawalEntity hotelCommissionWithdrawal) {
		hotelCommissionWithdrawalService.updateById(hotelCommissionWithdrawal);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelcommissionwithdrawal:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelCommissionWithdrawalService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
