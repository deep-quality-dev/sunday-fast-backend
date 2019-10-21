package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelHelepMenuEntity;
import io.renren.modules.hotel.service.HotelHelepMenuService;

/**
 * 帮助文档
 *
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 15:41:25
 */
@RestController
@RequestMapping("hotel/hotelhelepmenu")
public class HotelHelepMenuController {
	@Autowired
	private HotelHelepMenuService hotelHelepMenuService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelhelepmenu:list")
	public R list(@RequestParam Map<String, Object> params) {

		List<HotelHelepMenuEntity> hotelHelepMenuEntities = hotelHelepMenuService.list();
		for (HotelHelepMenuEntity hotelHelepMenuEntity : hotelHelepMenuEntities) {
			HotelHelepMenuEntity helepMenuEntity = hotelHelepMenuService.getById(hotelHelepMenuEntity.getPid());
			if (null != helepMenuEntity) {
				hotelHelepMenuEntity.setParentName(helepMenuEntity.getName());
			}
		}
		return R.ok(hotelHelepMenuEntities);
	}

	@RequestMapping("/select")
	@RequiresPermissions("hotel:hotelhelepmenu:list")
	public R select() {
		List<HotelHelepMenuEntity> hotelHelepMenuEntities = hotelHelepMenuService.list(Wrappers.<HotelHelepMenuEntity>lambdaQuery().eq(HotelHelepMenuEntity::getPid, 0));
		return R.ok(hotelHelepMenuEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelhelepmenu:info")
	public R info(@PathVariable("id") Long id) {
		HotelHelepMenuEntity hotelHelepMenu = hotelHelepMenuService.getById(id);

		return R.ok().put("hotelHelepMenu", hotelHelepMenu);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelhelepmenu:save")
	public R save(@RequestBody HotelHelepMenuEntity hotelHelepMenu) {
		hotelHelepMenuService.save(hotelHelepMenu);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelhelepmenu:update")
	public R update(@RequestBody HotelHelepMenuEntity hotelHelepMenu) {
		hotelHelepMenuService.updateById(hotelHelepMenu);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelhelepmenu:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelHelepMenuService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

	/**
	 * 帮助文档列表
	 * 
	 * @return
	 */
	@GetMapping("/helpMenus")
	public R helpMenus() {
		List<HotelHelepMenuEntity> hotelHelepMenuEntities = hotelHelepMenuService.list();
		return R.ok(hotelHelepMenuEntities);
	}

}
