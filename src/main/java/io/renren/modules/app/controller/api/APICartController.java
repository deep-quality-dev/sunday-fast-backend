package io.renren.modules.app.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.service.CartService;
import io.renren.modules.app.vo.CartVo;

/**
 * 购物车
 */
@RestController
@RequestMapping("app/cart")
public class APICartController {

	@Autowired
	private CartService cartService;

	/**
	 * 添加购物车
	 *
	 * @return
	 */
	@Login
	@PostMapping
	public R add(@RequestBody GoodsSku goodsSku, int num, @RequestAttribute("userId") Integer userId) {
		cartService.intoCart(goodsSku, num, userId);
		return R.ok();
	}

	/**
	 * 删除购物车
	 *
	 * @return
	 */
	@DeleteMapping
	public R del(@PathVariable Integer cartId, @RequestAttribute("userId") Integer userId) {
		cartService.delCart(cartId, userId);
		return R.ok();
	}

	/**
	 * 购物车列表
	 *
	 * @return
	 */
	@GetMapping
	public R list(@RequestAttribute("userId") Integer userId) {
		List<CartVo> cartVoList = new ArrayList<>();
		cartVoList = cartService.cartList(userId);
		return R.ok().put("data", cartVoList);
	}
}
