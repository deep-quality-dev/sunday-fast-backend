package io.renren.modules.hotel.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.form.AddCollectForm;
import io.renren.modules.hotel.service.HotelMemberCollectService;
import io.renren.modules.hotel.vo.CollectItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "收藏", tags = { "收藏接口" })
@RestController
@RequestMapping("/hotel/collect")
public class HotelCollectAPI extends BaseController {

	@Autowired
	private HotelMemberCollectService hotelMemberCollectService;

	@ApiOperation("添加收藏")
	@PostMapping
	@Login
	public R add(@RequestAttribute("userId") Long userId, @RequestBody AddCollectForm addCollectForm) {
		hotelMemberCollectService.addCollcet(userId, addCollectForm);
		return R.ok();
	}

	@ApiOperation("删除/取消收藏")
	@DeleteMapping
	@Login
	public R del(@RequestAttribute("userId") Long userId, @RequestBody AddCollectForm addCollectForm) {
		hotelMemberCollectService.delCollect(userId, addCollectForm);
		return R.ok();
	}

	@ApiOperation("收藏列表")
	@GetMapping("/page")
	@Login
	public R page(@RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit, int type) {
		Page<CollectItemVo> pageResult = hotelMemberCollectService.collectList(page, limit);
		return R.ok(pageResult);
	}
}
