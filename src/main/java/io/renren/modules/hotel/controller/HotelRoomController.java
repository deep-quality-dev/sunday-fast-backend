package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.form.RoomSwitchForm;
import io.renren.modules.hotel.form.SettingRoomStatusForm;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
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

	@Autowired
	private HotelRoomMoneyService hotelRoomMoneyService;

	@PostMapping("/roomSwitch")
	public R roomSwitch(@RequestBody RoomSwitchForm switchForm) {
		hotelRoomService.roomSwitch(switchForm.getId(), switchForm.getStatus(), switchForm.getDate());
		return R.ok();
	}

	@PostMapping("/moneySwitch")
	public R moneySwitch(@RequestBody RoomSwitchForm switchForm) {
		hotelRoomService.moneySwitch(switchForm.getId(), switchForm.getStatus(), switchForm.getDate());
		return R.ok();
	}

	/**
	 * 批量设置房态
	 * 
	 * @return
	 */
	@RequestMapping("/settingRoomStatusBatch")
	@RequiresPermissions("hotel:hotelroom:settingroomstatusbatch")
	public R settingRoomStatusBatch(@RequestBody SettingRoomStatusForm settingRoomStatusForms) {
		hotelRoomService.settingRoomStatusBatch(getSellerId(), settingRoomStatusForms);
		return R.ok();
	}

	@RequestMapping("/show/{id}")
	@RequiresPermissions("hotel:hotelroom:update")
	public R show(@PathVariable Long id) {
		hotelRoomService.show(getSellerId(), id);
		return R.ok();
	}

	@RequestMapping("/hide/{id}")
	@RequiresPermissions("hotel:hotelroom:update")
	public R hide(@PathVariable Long id) {
		hotelRoomService.hide(getSellerId(), id);
		return R.ok();
	}

	@GetMapping("/getAllRooms")
	@RequiresPermissions("hotel:hotelroom:list")
	public R getAllRooms() {
		List<HotelRoomEntity> hotelRoomEntities = hotelRoomService.list(Wrappers.<HotelRoomEntity>lambdaQuery().eq(HotelRoomEntity::getState, 1).eq(HotelRoomEntity::getSellerId, getSellerId()));
		for (HotelRoomEntity hotelRoomEntity : hotelRoomEntities) {
			List<HotelRoomMoneyEntity> hotelRoomMoney = hotelRoomMoneyService.list(Wrappers.<HotelRoomMoneyEntity>lambdaQuery().eq(HotelRoomMoneyEntity::getRoomId, hotelRoomEntity.getId()));
			hotelRoomEntity.setHotelRoomMoney(hotelRoomMoney);
		}
		return R.ok(hotelRoomEntities);
	}

	/**
	 * 房价数据
	 * 
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
		params.put("seller_id", 0L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelRoomService.queryPage(params);
		return R.ok().put("page", page);
	}

	@RequestMapping("/all")
	@RequiresPermissions("hotel:hotelroom:list")
	public R all() {
		List<HotelRoomEntity> hotelRoomEntities = hotelRoomService.list(new QueryWrapper<HotelRoomEntity>().eq("seller_id", getSellerId()).eq("state", 1));
		return R.ok().put("data", hotelRoomEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelroom:info")
	public R info(@PathVariable("id") Integer id) {
		HotelRoomEntity hotelRoom = hotelRoomService.getById(id);
		if (StrUtil.isNotEmpty(hotelRoom.getTags())) {
			hotelRoom.setTagList(Arrays.asList(hotelRoom.getTags().split(",")));
		}
		return R.ok().put("hotelroom", hotelRoom);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelroom:save")
	public R save(@RequestBody HotelRoomEntity hotelRoom) {
		hotelRoom.setSellerId(getSellerId());
		if (CollectionUtil.isNotEmpty(hotelRoom.getTagList())) {
			hotelRoom.setTags(CollectionUtil.join(hotelRoom.getTagList(), ","));
		}
		hotelRoomService.save(hotelRoom);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelroom:update")
	public R update(@RequestBody HotelRoomEntity hotelRoom) {
		if (CollectionUtil.isNotEmpty(hotelRoom.getTagList())) {
			hotelRoom.setTags(CollectionUtil.join(hotelRoom.getTagList(), ","));
		} else {
			hotelRoom.setTags("");
		}
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
