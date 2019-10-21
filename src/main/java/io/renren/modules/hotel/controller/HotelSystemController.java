package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelSystemEntity;
import io.renren.modules.hotel.service.HotelSystemService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:32
 */
@RestController
@RequestMapping("hotel/hotelsystem")
public class HotelSystemController {
	@Autowired
	private HotelSystemService hotelSystemService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelsystem:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelSystemService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info")
	@RequiresPermissions("hotel:hotelsystem:info")
	public R info() {
		HotelSystemEntity hotelSystem = hotelSystemService.getOne(Wrappers.lambdaQuery());
		return R.ok().put("hotelSystem", hotelSystem);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelsystem:save")
	public R save(@RequestBody HotelSystemEntity hotelSystem) {
		HotelSystemEntity dbHotelSystemEntity = hotelSystemService.getOne(Wrappers.lambdaQuery());
		if (null == dbHotelSystemEntity) {
			dbHotelSystemEntity = new HotelSystemEntity();
		}
		dbHotelSystemEntity.setCompanyAddress(hotelSystem.getCompanyAddress());
		dbHotelSystemEntity.setCompanyEmail(hotelSystem.getCompanyEmail());
		dbHotelSystemEntity.setCompanyName(hotelSystem.getCompanyName());
		dbHotelSystemEntity.setCompanyPhone(hotelSystem.getCompanyPhone());
		dbHotelSystemEntity.setBusinessLicense(hotelSystem.getBusinessLicense());
		dbHotelSystemEntity.setLicence(hotelSystem.getLicence());
		dbHotelSystemEntity.setPlatformInfo(hotelSystem.getPlatformInfo());
		dbHotelSystemEntity.setLinkName(hotelSystem.getLinkName());
		dbHotelSystemEntity.setLinkLogo(hotelSystem.getLinkLogo());
		hotelSystemService.saveOrUpdate(dbHotelSystemEntity);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/withdrawSetting")
	@RequiresPermissions("hotel:hotelsystem:update")
	public R withdrawSetting(@RequestBody HotelSystemEntity hotelSystem) {
		HotelSystemEntity dbHotelSystemEntity = hotelSystemService.getOne(Wrappers.lambdaQuery());
		if (null == dbHotelSystemEntity) {
			dbHotelSystemEntity = new HotelSystemEntity();
		}
		dbHotelSystemEntity.setZdMoney(hotelSystem.getZdMoney());
		dbHotelSystemEntity.setTxMode(hotelSystem.getTxMode());
		dbHotelSystemEntity.setTxNotice(hotelSystem.getTxNotice());
		dbHotelSystemEntity.setTxSxf(hotelSystem.getTxSxf());
		hotelSystemService.saveOrUpdate(dbHotelSystemEntity);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelsystem:update")
	public R update(@RequestBody HotelSystemEntity hotelSystem) {
		hotelSystemService.updateById(hotelSystem);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelsystem:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelSystemService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
