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
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 会员卡详情
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-17 19:46:29
 */
@RestController
@RequestMapping("hotel/hotelmemberleveldetail")
public class HotelMemberLevelDetailController extends AbstractController {
	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelmemberleveldetail:list")
	public R list(@RequestParam Map<String, Object> params) {
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelMemberLevelDetailService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelmemberleveldetail:info")
	public R info(@PathVariable("id") Long id) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetail = hotelMemberLevelDetailService.getById(id);

		return R.ok().put("hotelMemberLevelDetail", hotelMemberLevelDetail);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelmemberleveldetail:save")
	public R save(@RequestBody HotelMemberLevelDetailEntity hotelMemberLevelDetail) {
		hotelMemberLevelDetailService.save(hotelMemberLevelDetail);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelmemberleveldetail:update")
	public R update(@RequestBody HotelMemberLevelDetailEntity hotelMemberLevelDetail) {
		hotelMemberLevelDetailService.updateById(hotelMemberLevelDetail);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelmemberleveldetail:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelMemberLevelDetailService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
