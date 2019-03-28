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
import io.renren.modules.hotel.entity.HotelPrinterEntity;
import io.renren.modules.hotel.service.HotelPrinterService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@RestController
@RequestMapping("hotel/hotelprinter")
public class HotelPrinterController {
	@Autowired
	private HotelPrinterService hotelPrinterService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelprinter:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelPrinterService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelprinter:info")
	public R info(@PathVariable("id") Integer id) {
		HotelPrinterEntity hotelPrinter = hotelPrinterService.getById(id);

		return R.ok().put("hotelPrinter", hotelPrinter);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelprinter:save")
	public R save(@RequestBody HotelPrinterEntity hotelPrinter) {
		hotelPrinterService.save(hotelPrinter);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelprinter:update")
	public R update(@RequestBody HotelPrinterEntity hotelPrinter) {
		hotelPrinterService.updateById(hotelPrinter);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelprinter:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelPrinterService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
