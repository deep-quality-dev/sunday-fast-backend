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
import io.renren.modules.hotel.entity.HotelAccountEntity;
import io.renren.modules.hotel.service.HotelAccountService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
@RestController
@RequestMapping("hotel/hotelaccount")
public class HotelAccountController {
	@Autowired
	private HotelAccountService hotelAccountService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelaccount:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelAccountService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelaccount:info")
	public R info(@PathVariable("id") Integer id) {
		HotelAccountEntity hotelAccount = hotelAccountService.getById(id);

		return R.ok().put("hotelAccount", hotelAccount);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelaccount:save")
	public R save(@RequestBody HotelAccountEntity hotelAccount) {
		hotelAccountService.save(hotelAccount);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelaccount:update")
	public R update(@RequestBody HotelAccountEntity hotelAccount) {
		hotelAccountService.updateById(hotelAccount);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelaccount:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelAccountService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
