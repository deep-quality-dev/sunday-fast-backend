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

import com.alibaba.fastjson.JSON;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.service.HotelMemberLevelService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 会员等级表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@RestController
@RequestMapping("hotel/hotelmemberlevel")
public class HotelMemberLevelController extends AbstractController {
	@Autowired
	private HotelMemberLevelService hotelMemberLevelService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelmemberlevel:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("seller_id", 0L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelMemberLevelService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelmemberlevel:info")
	public R info(@PathVariable("id") Integer id) {
		HotelMemberLevelEntity hotelMemberLevel = hotelMemberLevelService.getById(id);

		return R.ok().put("hotelMemberLevel", hotelMemberLevel);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelmemberlevel:save")
	public R save(@RequestBody HotelMemberLevelEntity hotelMemberLevel) {
		hotelMemberLevel.setSellerId(0L);
		if (!isAdmin()) {
			hotelMemberLevel.setSellerId(getSellerId());
		}
		if (StrUtil.isNotEmpty(hotelMemberLevel.getBgImage())) {
			String result = HttpUtil.get(hotelMemberLevel.getBgImage() + "?x-oss-process=image/average-hue");
			hotelMemberLevel.setRgb(JSON.parseObject(result).getString("RGB"));
		}
		hotelMemberLevelService.save(hotelMemberLevel);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelmemberlevel:update")
	public R update(@RequestBody HotelMemberLevelEntity hotelMemberLevel) {
		if (StrUtil.isNotEmpty(hotelMemberLevel.getBgImage())) {
			String result = HttpUtil.get(hotelMemberLevel.getBgImage() + "?x-oss-process=image/average-hue");
			hotelMemberLevel.setRgb(JSON.parseObject(result).getString("RGB"));
		}
		hotelMemberLevelService.updateById(hotelMemberLevel);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelmemberlevel:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelMemberLevelService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
