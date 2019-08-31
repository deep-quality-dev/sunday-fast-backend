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
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.service.HotelMemberService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@RestController
@RequestMapping("hotel/hotelmember")
public class HotelMemberController {
	@Autowired
	private HotelMemberService hotelMemberService;

	/**
	 * 用户列表列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelmember:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelMemberService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelmember:info")
	public R info(@PathVariable("id") Integer id) {
		HotelMemberEntity hotelMember = hotelMemberService.getById(id);

		return R.ok().put("hotelMember", hotelMember);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelmember:save")
	public R save(@RequestBody HotelMemberEntity hotelMember) {
		hotelMemberService.save(hotelMember);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelmember:update")
	public R update(@RequestBody HotelMemberEntity hotelMember) {
		hotelMemberService.updateById(hotelMember);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelmember:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelMemberService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
