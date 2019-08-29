package io.renren.modules.app.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.vo.GoodsVo;
import io.renren.modules.app.vo.OrderVo;

/**
 * 订单
 */
@RestController
@RequestMapping("app/order")
public class APIOrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 订单列表
	 *
	 * @param userId
	 * @return
	 */
	@Login
	@GetMapping("/list")
	public R list(@RequestAttribute("userId") Integer userId, Page<GoodsVo> page, Integer status) {
		Page<OrderVo> orderListPage = orderService.orderList(page, status, userId);
		return R.ok().put("data", orderListPage);
	}

	/**
	 * 订单详情
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	@Login
	@GetMapping("/detail/{id}")
	public R detail(@PathVariable Integer id, @RequestAttribute("userId") Integer userId) {
		OrderVo orderVo = orderService.orderDetail(id, userId);
		return R.ok().put("data", orderVo);
	}

	/**
	 * 删除订单
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	@Login
	@DeleteMapping("/{orderId}")
	public R del(@PathVariable(name = "orderId") Integer orderId, @RequestAttribute("userId") Integer userId) {
		orderService.delOrder(orderId, userId);
		return R.ok();
	}
}
