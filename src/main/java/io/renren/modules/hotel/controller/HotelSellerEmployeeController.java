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

import io.renren.modules.hotel.entity.HotelSellerEmployeeEntity;
import io.renren.modules.hotel.service.HotelSellerEmployeeService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 22:32:03
 */
@RestController
@RequestMapping("hotel/hotelselleremployee")
public class HotelSellerEmployeeController extends AbstractController {
	@Autowired
	private HotelSellerEmployeeService hotelSellerEmployeeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelselleremployee:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("seller_id", getSellerId());
		PageUtils page = hotelSellerEmployeeService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelselleremployee:info")
	public R info(@PathVariable("id") Long id) {
		HotelSellerEmployeeEntity hotelSellerEmployee = hotelSellerEmployeeService.getById(id);

		return R.ok().put("hotelSellerEmployee", hotelSellerEmployee);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelselleremployee:save")
	public R save(@RequestBody HotelSellerEmployeeEntity hotelSellerEmployee) {
		hotelSellerEmployee.setSellerId(getSellerId());
		hotelSellerEmployeeService.saveEmployee(hotelSellerEmployee, getUserId());
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelselleremployee:update")
	public R update(@RequestBody HotelSellerEmployeeEntity hotelSellerEmployee) {
		hotelSellerEmployeeService.updateEmployee(hotelSellerEmployee);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelselleremployee:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelSellerEmployeeService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
