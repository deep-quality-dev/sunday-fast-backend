package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 优惠券
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@RestController
@RequestMapping("hotel/hotelcoupons")
public class HotelCouponsController extends AbstractController {
	@Autowired
	private HotelCouponsService hotelCouponsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelcoupons:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("seller_id", -1L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelCouponsService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions("hotel:hotelcoupons:list")
	public R listAll(@RequestParam Map<String, Object> params) {
		params.put("seller_id", -1L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		List<HotelCouponsEntity> hotelCouponsEntities = hotelCouponsService.list(Wrappers.<HotelCouponsEntity>lambdaQuery().eq(HotelCouponsEntity::getSellerId, getSellerId()).gt(HotelCouponsEntity::getEndTime, DateUtil.formatDate(DateUtil.date())));
		return R.ok(hotelCouponsEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelcoupons:info")
	public R info(@PathVariable("id") Integer id) {
		HotelCouponsEntity hotelCoupons = hotelCouponsService.getById(id);

		return R.ok().put("hotelCoupons", hotelCoupons);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelcoupons:save")
	public R save(@RequestBody HotelCouponsEntity hotelCoupons) {
		hotelCoupons.setSellerId(-1L);
		if (!isAdmin()) {
			hotelCoupons.setSellerId(getSellerId());
		}
		hotelCouponsService.save(hotelCoupons);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelcoupons:update")
	public R update(@RequestBody HotelCouponsEntity hotelCoupons) {
		hotelCouponsService.updateById(hotelCoupons);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelcoupons:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelCouponsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
