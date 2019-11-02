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

import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.service.HotelAssessService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 评价表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
@RestController
@RequestMapping("hotel/hotelassess")
public class HotelAssessController extends AbstractController {
	@Autowired
	private HotelAssessService hotelAssessService;

	/**
	 * 回复
	 * 
	 * @param assessEntity
	 * @return
	 */
	@RequestMapping("/reply")
	@RequiresPermissions("hotel:hotelassess:reply")
	public R reply(HotelAssessEntity assessEntity) {
		hotelAssessService.updateById(assessEntity);
		return R.ok();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelassess:list")
	public R list(@RequestParam Map<String, Object> params) {
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		PageUtils page = hotelAssessService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelassess:info")
	public R info(@PathVariable("id") Integer id) {
		HotelAssessEntity hotelAssess = hotelAssessService.getById(id);

		return R.ok().put("hotelAssess", hotelAssess);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelassess:save")
	public R save(@RequestBody HotelAssessEntity hotelAssess) {
		hotelAssessService.save(hotelAssess);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelassess:update")
	public R update(@RequestBody HotelAssessEntity hotelAssess) {
		HotelAssessEntity dbHotelAssess = hotelAssessService.getById(hotelAssess.getId());
		dbHotelAssess.setReplyTime(DateUtil.date().getTime());
		dbHotelAssess.setReply(hotelAssess.getReply());
		dbHotelAssess.setStatus(2);
		hotelAssessService.updateById(dbHotelAssess);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelassess:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelAssessService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
