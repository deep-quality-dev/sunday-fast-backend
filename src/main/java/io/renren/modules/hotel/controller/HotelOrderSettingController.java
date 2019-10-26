package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelOrderSettingDateEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingRoomEntity;
import io.renren.modules.hotel.service.HotelOrderSettingDateService;
import io.renren.modules.hotel.service.HotelOrderSettingRoomService;
import io.renren.modules.hotel.service.HotelOrderSettingService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 19:16:27
 */
@RestController
@RequestMapping("hotel/hotelordersetting")
public class HotelOrderSettingController extends AbstractController {
	@Autowired
	private HotelOrderSettingService hotelOrderSettingService;

	@Autowired
	private HotelOrderSettingRoomService hotelOrderSettingRoomService;

	@Autowired
	private HotelOrderSettingDateService hotelOrderSettingDateService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelordersetting:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelOrderSettingService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info")
	@RequiresPermissions("hotel:hotelordersetting:info")
	public R info() {
		HotelOrderSettingEntity hotelOrderSetting = hotelOrderSettingService.getOne(Wrappers.<HotelOrderSettingEntity>lambdaQuery().eq(HotelOrderSettingEntity::getSellerId, getSellerId()));
		List<HotelOrderSettingDateEntity> orderSettingDateEntities = hotelOrderSettingDateService.list(Wrappers.<HotelOrderSettingDateEntity>lambdaQuery().eq(HotelOrderSettingDateEntity::getSettingId, hotelOrderSetting.getId()));
		hotelOrderSetting.setDays(orderSettingDateEntities);
		List<HotelOrderSettingRoomEntity> hotelOrderSettingRoomEntities = hotelOrderSettingRoomService.list(Wrappers.<HotelOrderSettingRoomEntity>lambdaQuery().eq(HotelOrderSettingRoomEntity::getSettingId, hotelOrderSetting.getId()));
		hotelOrderSetting.setRooms(hotelOrderSettingRoomEntities);
		return R.ok().put("hotelOrderSetting", hotelOrderSetting);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelordersetting:save")
	public R save(@RequestBody HotelOrderSettingEntity hotelOrderSetting) {
		hotelOrderSetting.setSellerId(getSellerId());
		hotelOrderSettingService.saveOrderSetting(hotelOrderSetting);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelordersetting:update")
	public R update(@RequestBody HotelOrderSettingEntity hotelOrderSetting) {
		hotelOrderSettingService.updateOrderSetting(hotelOrderSetting);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelordersetting:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelOrderSettingService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
