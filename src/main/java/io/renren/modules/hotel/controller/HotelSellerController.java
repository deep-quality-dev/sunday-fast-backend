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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@RestController
@RequestMapping("hotel/hotelseller")
public class HotelSellerController extends AbstractController {
	@Autowired
	private HotelSellerService hotelSellerService;

	@RequestMapping("/store")
	@RequiresPermissions("hotel:hotelseller:store")
	public R store() {
		HotelSellerEntity hotelSeller = hotelSellerService.getOne(new QueryWrapper<HotelSellerEntity>().eq("user_id", getUserId()).eq("state", 2));
		return R.ok().put("hotelSeller", hotelSeller);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelseller:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelSellerService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelseller:info")
	public R info(@PathVariable("id") Integer id) {
		HotelSellerEntity hotelSeller = hotelSellerService.getById(id);

		return R.ok().put("hotelSeller", hotelSeller);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelseller:save")
	public R save(@RequestBody HotelSellerEntity hotelSeller) {
		hotelSeller.setUserId(getUserId());
		hotelSellerService.save(hotelSeller);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelseller:update")
	public R update(@RequestBody HotelSellerEntity hotelSeller) {
		hotelSellerService.updateById(hotelSeller);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelseller:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelSellerService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
