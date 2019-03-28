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
import io.renren.modules.hotel.entity.HotelIntegralRedPacketEntity;
import io.renren.modules.hotel.service.HotelIntegralRedPacketService;

/**
 * 积分红包表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@RestController
@RequestMapping("hotel/hotelintegralredpacket")
public class HotelIntegralRedPacketController {
	@Autowired
	private HotelIntegralRedPacketService hotelIntegralRedPacketService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelintegralredpacket:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelIntegralRedPacketService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelintegralredpacket:info")
	public R info(@PathVariable("id") Integer id) {
		HotelIntegralRedPacketEntity hotelIntegralRedPacket = hotelIntegralRedPacketService.getById(id);

		return R.ok().put("hotelIntegralRedPacket", hotelIntegralRedPacket);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelintegralredpacket:save")
	public R save(@RequestBody HotelIntegralRedPacketEntity hotelIntegralRedPacket) {
		hotelIntegralRedPacketService.save(hotelIntegralRedPacket);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelintegralredpacket:update")
	public R update(@RequestBody HotelIntegralRedPacketEntity hotelIntegralRedPacket) {
		hotelIntegralRedPacketService.updateById(hotelIntegralRedPacket);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelintegralredpacket:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelIntegralRedPacketService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
