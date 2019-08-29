package io.renren.modules.app.controller.admin;

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
import io.renren.modules.app.entity.OrderGoodsEntity;
import io.renren.modules.app.service.OrderGoodsService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/ordergoods")
public class OrderGoodsController {
	@Autowired
	private OrderGoodsService orderGoodsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:ordergoods:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderGoodsService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:ordergoods:info")
	public R info(@PathVariable("id") Integer id) {
		OrderGoodsEntity orderGoods = orderGoodsService.getById(id);

		return R.ok().put("orderGoods", orderGoods);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:ordergoods:save")
	public R save(@RequestBody OrderGoodsEntity orderGoods) {
		orderGoodsService.save(orderGoods);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:ordergoods:update")
	public R update(@RequestBody OrderGoodsEntity orderGoods) {
		orderGoodsService.updateById(orderGoods);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:ordergoods:delete")
	public R delete(@RequestBody Integer[] ids) {
		orderGoodsService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
