package io.renren.modules.hotel.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;

import io.renren.common.utils.NetworkUtil;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.form.BuildOrderForm;
import io.renren.modules.hotel.service.HotelOrderService;
import io.renren.modules.hotel.vo.HotelOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 酒店订单
 * 
 * @author taoz
 *
 */
@Slf4j
@Api(value = "酒店订单接口", tags = { "酒店订单接口" })
@RestController
@RequestMapping("/hotel/order")
public class HotelOrderAPI extends BaseController {
	@Autowired
	private HotelOrderService hotelOrderService;

	/**
	 * 构建订单信息
	 * 
	 * @return
	 */
	@Login
	@ApiOperation("获取订单信息")
	@GetMapping("/buildOrder")
	public R buildOrder(@RequestParam Long roomId, @RequestAttribute("userId") Long userId, @RequestParam Long moneyId, int roomNum, Long contactsId, String checkInDate, String checkOutDate) {
		BuildOrderForm buildOrderForm = hotelOrderService.buildOrder(userId, roomId, moneyId, contactsId, roomNum, checkInDate, checkOutDate);
		return R.ok().put("data", buildOrderForm);
	}

	/**
	 * 创建订单
	 * 
	 * @return
	 */
	@Login
	@ApiOperation("创建订单")
	@PostMapping("/createOrder")
	public R createOrder(HttpServletRequest request, @RequestBody BuildOrderForm buildOrderForm, @RequestAttribute("userId") Long userId) {
		// 表单校验
		ValidatorUtils.validateEntity(buildOrderForm);
		WxPayMpOrderResult mpOrderResult = null;
		try {
			buildOrderForm.setIp(NetworkUtil.getIpAddress(request));
		} catch (IOException e1) {
			log.error("创建订单异常，msg:{}", e1.getMessage());
			return R.error("创建订单失败，请稍后再试");
		}
		try {
			mpOrderResult = hotelOrderService.createOrder(buildOrderForm, userId);
		} catch (WxPayException e) {
			log.error("创建订单异常，msg:{}", e.getMessage());
			return R.error("创建订单失败，请稍后再试");
		}
		return R.ok().put("data", mpOrderResult);
	}

	/**
	 * 会员订单列表
	 * 
	 * @param userId
	 * @return
	 */
	@Login
	@ApiOperation("会员订单列表")
	@GetMapping("/orderList")
	public R orderList(@RequestAttribute("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit, @RequestParam(required = false) Integer orderStatus) {
		Page<HotelOrderVo> pageResult = hotelOrderService.userOrderList(userId, orderStatus, page, limit);
		return R.ok().put("data", pageResult);
	}

	/**
	 * 订单详情
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@Login
	@ApiOperation("会员订单详情")
	@GetMapping("/orderDetail/{orderId}")
	public R orderDetail(@RequestAttribute("userId") Long userId, @PathVariable Long orderId) {
		HotelOrderVo hotelOrderVo = hotelOrderService.orderDetail(userId, orderId);
		return R.ok().put("data", hotelOrderVo);
	}

	/**
	 * 取消订单
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@Login
	@ApiOperation("取消订单")
	@PutMapping("/cancelOrder/{orderId}")
	public R cancelOrder(@RequestAttribute("userId") Long userId, @PathVariable Long orderId) {
		hotelOrderService.cancelOrder(userId, orderId);
		return R.ok();
	}

	/**
	 * 删除订单
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@Login
	@ApiOperation("取消订单")
	@DeleteMapping("/deleteOrder/{orderId}")
	public R deleteOrder(@RequestAttribute("userId") Long userId, @PathVariable Long orderId) {
		hotelOrderService.deleteOrder(userId, orderId);
		return R.ok();
	}

	/**
	 * 订单支付
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@Login
	@ApiOperation("订单支付")
	@PostMapping("/payOrder")
	public R payOrder(@RequestAttribute("userId") Long userId, @RequestParam Long orderId) {
		try {
			hotelOrderService.payOrder(userId, orderId, "127.0.0.1");
		} catch (WxPayException e) {
			e.printStackTrace();
		}
		return R.ok();
	}
}
