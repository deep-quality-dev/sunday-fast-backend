package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-21 22:44:37
 */
@RestController
@RequestMapping("hotel/hotelroommoney")
public class HotelRoomMoneyController extends AbstractController {
	@Autowired
	private HotelRoomMoneyService hotelRoomMoneyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelroommoney:list")
	public R list(Long roomId) {
		List<HotelRoomMoneyEntity> list = hotelRoomMoneyService.list(Wrappers.<HotelRoomMoneyEntity>lambdaQuery().eq(HotelRoomMoneyEntity::getRoomId, roomId));
		return R.ok(list);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelroommoney:info")
	public R info(@PathVariable("id") Long id) {
		HotelRoomMoneyEntity hotelRoomMoney = hotelRoomMoneyService.getById(id);

		return R.ok().put("hotelRoomMoney", hotelRoomMoney);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelroommoney:save")
	public R save(@RequestBody HotelRoomMoneyEntity hotelRoomMoney) {
		hotelRoomMoney.setSellerId(getSellerId());
		hotelRoomMoneyService.saveRoomMoney(hotelRoomMoney);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelroommoney:update")
	public R update(@RequestBody HotelRoomMoneyEntity hotelRoomMoney) {
		hotelRoomMoneyService.updateById(hotelRoomMoney);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelroommoney:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelRoomMoneyService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
