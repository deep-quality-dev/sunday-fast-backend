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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelCouponsBreakfastEntity;
import io.renren.modules.hotel.service.HotelCouponsBreakfastService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 早餐券
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-26 22:10:49
 */
@RestController
@RequestMapping("hotel/hotelcouponsbreakfast")
public class HotelCouponsBreakfastController extends AbstractController {
	@Autowired
	private HotelCouponsBreakfastService hotelCouponsBreakfastService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelcouponsbreakfast:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("seller_id", -1L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelCouponsBreakfastService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions("hotel:hotelcouponsbreakfast:list")
	public R listAll(@RequestParam Map<String, Object> params) {
		params.put("seller_id", -1L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		List<HotelCouponsBreakfastEntity> couponsBreakfastEntities = hotelCouponsBreakfastService.list(Wrappers.<HotelCouponsBreakfastEntity>lambdaQuery().eq(HotelCouponsBreakfastEntity::getSellerId, getSellerId()).gt(HotelCouponsBreakfastEntity::getEndTime, DateUtil.formatDate(DateUtil.date())));
		return R.ok(couponsBreakfastEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelcouponsbreakfast:info")
	public R info(@PathVariable("id") Long id) {
		HotelCouponsBreakfastEntity hotelCouponsBreakfast = hotelCouponsBreakfastService.getById(id);

		return R.ok().put("hotelCouponsBreakfast", hotelCouponsBreakfast);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelcouponsbreakfast:save")
	public R save(@RequestBody HotelCouponsBreakfastEntity hotelCouponsBreakfast) {
		hotelCouponsBreakfast.setSellerId(-1L);
		if (!isAdmin()) {
			hotelCouponsBreakfast.setSellerId(getSellerId());
		}
		hotelCouponsBreakfastService.save(hotelCouponsBreakfast);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelcouponsbreakfast:update")
	public R update(@RequestBody HotelCouponsBreakfastEntity hotelCouponsBreakfast) {
		hotelCouponsBreakfastService.updateById(hotelCouponsBreakfast);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelcouponsbreakfast:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelCouponsBreakfastService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
