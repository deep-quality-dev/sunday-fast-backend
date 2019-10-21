package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.hotel.entity.HotelFriendLinkEntity;
import io.renren.modules.hotel.service.HotelFriendLinkService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

/**
 * 友情链接
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 15:41:25
 */
@RestController
@RequestMapping("hotel/hotelfriendlink")
public class HotelFriendLinkController {
	@Autowired
	private HotelFriendLinkService hotelFriendLinkService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelfriendlink:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelFriendLinkService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelfriendlink:info")
	public R info(@PathVariable("id") Long id) {
		HotelFriendLinkEntity hotelFriendLink = hotelFriendLinkService.getById(id);

		return R.ok().put("hotelFriendLink", hotelFriendLink);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelfriendlink:save")
	public R save(@RequestBody HotelFriendLinkEntity hotelFriendLink) {
		hotelFriendLinkService.save(hotelFriendLink);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelfriendlink:update")
	public R update(@RequestBody HotelFriendLinkEntity hotelFriendLink) {
		hotelFriendLinkService.updateById(hotelFriendLink);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelfriendlink:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelFriendLinkService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

	@GetMapping("/allList")
	public R list() {
		List<HotelFriendLinkEntity> hotelFriendLinkEntities = hotelFriendLinkService.list();
		return R.ok(hotelFriendLinkEntities);
	}

}
