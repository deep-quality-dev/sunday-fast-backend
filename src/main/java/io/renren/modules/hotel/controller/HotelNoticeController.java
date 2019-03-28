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
import io.renren.modules.hotel.entity.HotelNoticeEntity;
import io.renren.modules.hotel.service.HotelNoticeService;

/**
 * 通知表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@RestController
@RequestMapping("hotel/hotelnotice")
public class HotelNoticeController {
	@Autowired
	private HotelNoticeService hotelNoticeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelnotice:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelNoticeService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelnotice:info")
	public R info(@PathVariable("id") Integer id) {
		HotelNoticeEntity hotelNotice = hotelNoticeService.getById(id);

		return R.ok().put("hotelNotice", hotelNotice);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelnotice:save")
	public R save(@RequestBody HotelNoticeEntity hotelNotice) {
		hotelNoticeService.save(hotelNotice);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelnotice:update")
	public R update(@RequestBody HotelNoticeEntity hotelNotice) {
		hotelNoticeService.updateById(hotelNotice);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelnotice:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelNoticeService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
