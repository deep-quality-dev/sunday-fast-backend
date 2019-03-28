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
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
import io.renren.modules.hotel.service.HotelRoomNumService;

/**
 * 房量
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:34
 */
@RestController
@RequestMapping("hotel/hotelroomnum")
public class HotelRoomNumController {
	@Autowired
	private HotelRoomNumService hotelRoomNumService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelroomnum:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelRoomNumService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelroomnum:info")
	public R info(@PathVariable("id") Integer id) {
		HotelRoomNumEntity hotelRoomNum = hotelRoomNumService.getById(id);

		return R.ok().put("hotelRoomNum", hotelRoomNum);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelroomnum:save")
	public R save(@RequestBody HotelRoomNumEntity hotelRoomNum) {
		hotelRoomNumService.save(hotelRoomNum);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelroomnum:update")
	public R update(@RequestBody HotelRoomNumEntity hotelRoomNum) {
		hotelRoomNumService.updateById(hotelRoomNum);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelroomnum:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelRoomNumService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
