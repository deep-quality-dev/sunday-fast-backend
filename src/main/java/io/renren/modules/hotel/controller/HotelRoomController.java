package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 房型信息
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@RestController
@RequestMapping("hotel/hotelroom")
public class HotelRoomController extends AbstractController {
	@Autowired
	private HotelRoomService hotelRoomService;

	/**
	 * 房价数据
	 * @return
	 */
	@RequestMapping("/roomPriceList")
	@RequiresPermissions("hotel:hotelroom:roompricelist")
	public R roomPriceList() {
		
		return R.ok();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelroom:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelRoomService.queryPage(params);

		return R.ok().put("page", page);
	}

	@RequestMapping("/all")
	@RequiresPermissions("hotel:hotelroom:list")
	public R all() {
		List<HotelRoomEntity> hotelRoomEntities = hotelRoomService
				.list(new QueryWrapper<HotelRoomEntity>().eq("seller_id", getSellerId()).eq("state", 1));
		return R.ok().put("data", hotelRoomEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelroom:info")
	public R info(@PathVariable("id") Integer id) {
		HotelRoomEntity hotelRoom = hotelRoomService.getById(id);

		return R.ok().put("hotelroom", hotelRoom);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelroom:save")
	public R save(@RequestBody HotelRoomEntity hotelRoom) {
		hotelRoom.setSellerId(getSellerId());
		hotelRoomService.save(hotelRoom);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelroom:update")
	public R update(@RequestBody HotelRoomEntity hotelRoom) {
		hotelRoomService.updateById(hotelRoom);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelroom:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelRoomService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
