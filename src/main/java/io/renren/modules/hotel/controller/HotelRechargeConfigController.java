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
import io.renren.modules.hotel.entity.HotelRechargeConfigEntity;
import io.renren.modules.hotel.service.HotelRechargeConfigService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-26 22:10:48
 */
@RestController
@RequestMapping("hotel/hotelrechargeconfig")
public class HotelRechargeConfigController extends AbstractController{
	@Autowired
	private HotelRechargeConfigService hotelRechargeConfigService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelrechargeconfig:list")
	public R list(@RequestParam Map<String, Object> params) {
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelRechargeConfigService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelrechargeconfig:info")
	public R info(@PathVariable("id") Integer id) {
		HotelRechargeConfigEntity hotelRechargeConfig = hotelRechargeConfigService.getById(id);

		return R.ok().put("hotelRechargeConfig", hotelRechargeConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelrechargeconfig:save")
	public R save(@RequestBody HotelRechargeConfigEntity hotelRechargeConfig) {
		if (!isAdmin()) {
			hotelRechargeConfig.setSellerId(getSellerId());
		}
		
		hotelRechargeConfigService.save(hotelRechargeConfig);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelrechargeconfig:update")
	public R update(@RequestBody HotelRechargeConfigEntity hotelRechargeConfig) {
		hotelRechargeConfigService.updateById(hotelRechargeConfig);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelrechargeconfig:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelRechargeConfigService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
