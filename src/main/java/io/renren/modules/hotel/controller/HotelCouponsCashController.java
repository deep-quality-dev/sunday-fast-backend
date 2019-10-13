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
import io.renren.modules.hotel.entity.HotelCouponsCashEntity;
import io.renren.modules.hotel.service.HotelCouponsCashService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 代金券
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-26 22:10:49
 */
@RestController
@RequestMapping("hotel/hotelcouponscash")
public class HotelCouponsCashController extends AbstractController{
	@Autowired
	private HotelCouponsCashService hotelCouponsCashService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelcouponscash:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("seller_id", -1L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelCouponsCashService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelcouponscash:info")
	public R info(@PathVariable("id") Long id) {
		HotelCouponsCashEntity hotelCouponsCash = hotelCouponsCashService.getById(id);

		return R.ok().put("hotelCouponsCash", hotelCouponsCash);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelcouponscash:save")
	public R save(@RequestBody HotelCouponsCashEntity hotelCouponsCash) {
		hotelCouponsCash.setSellerId(1L);
		hotelCouponsCashService.save(hotelCouponsCash);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelcouponscash:update")
	public R update(@RequestBody HotelCouponsCashEntity hotelCouponsCash) {
		hotelCouponsCashService.updateById(hotelCouponsCash);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelcouponscash:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelCouponsCashService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
