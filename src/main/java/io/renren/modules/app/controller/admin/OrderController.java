package io.renren.modules.app.controller.admin;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.service.OrderService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("admin/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:order:list")
	public R list(Page page, OrderEntity order) {
		return R.ok().put("data", orderService.orderPage(page, order));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:order:info")
	public R info(@PathVariable("id") Integer id) {
		OrderEntity order = orderService.getById(id);

		return R.ok().put("data", order);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:order:save")
	public R save(@RequestBody OrderEntity order) {
		orderService.save(order);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:order:update")
	public R update(@RequestBody OrderEntity order) {
		orderService.updateById(order);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:order:delete")
	public R delete(@RequestBody Integer[] ids) {
		orderService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
