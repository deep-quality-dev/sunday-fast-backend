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
import io.renren.modules.hotel.entity.HotelAdvertisingEntity;
import io.renren.modules.hotel.service.HotelAdvertisingService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:40
 */
@RestController
@RequestMapping("hotel/hoteladvertising")
public class HotelAdvertisingController extends AbstractController {
	@Autowired
	private HotelAdvertisingService hotelAdvertisingService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hoteladvertising:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelAdvertisingService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hoteladvertising:info")
	public R info(@PathVariable("id") Integer id) {
		HotelAdvertisingEntity hotelAdvertising = hotelAdvertisingService.getById(id);

		return R.ok().put("hotelAdvertising", hotelAdvertising);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hoteladvertising:save")
	public R save(@RequestBody HotelAdvertisingEntity hotelAdvertising) {
		hotelAdvertising.setSellerId(getSellerId());
		hotelAdvertisingService.save(hotelAdvertising);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hoteladvertising:update")
	public R update(@RequestBody HotelAdvertisingEntity hotelAdvertising) {
		hotelAdvertisingService.updateById(hotelAdvertising);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hoteladvertising:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelAdvertisingService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
