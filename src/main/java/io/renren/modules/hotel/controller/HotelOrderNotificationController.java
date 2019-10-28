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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.modules.hotel.entity.HotelOrderNotificationEntity;
import io.renren.modules.hotel.service.HotelOrderNotificationService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-26 17:47:02
 */
@RestController
@RequestMapping("hotel/hotelordernotification")
public class HotelOrderNotificationController extends AbstractController {
	@Autowired
	private HotelOrderNotificationService hotelOrderNotificationService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelordernotification:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelOrderNotificationService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info")
	@RequiresPermissions("hotel:hotelordernotification:info")
	public R info() {
		HotelOrderNotificationEntity hotelOrderNotification = hotelOrderNotificationService.getOne(Wrappers.<HotelOrderNotificationEntity>lambdaQuery().eq(HotelOrderNotificationEntity::getSellerId, getSellerId()));
		return R.ok().put("hotelOrderNotification", hotelOrderNotification);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelordernotification:save")
	public R save(@RequestBody HotelOrderNotificationEntity hotelOrderNotification) {
		hotelOrderNotification.setSellerId(getSellerId());
		hotelOrderNotificationService.save(hotelOrderNotification);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelordernotification:update")
	public R update(@RequestBody HotelOrderNotificationEntity hotelOrderNotification) {
		hotelOrderNotification.setSellerId(getSellerId());
		hotelOrderNotificationService.updateById(hotelOrderNotification);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelordernotification:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelOrderNotificationService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
