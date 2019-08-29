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
import io.renren.modules.app.entity.CouponEntity;
import io.renren.modules.app.service.CouponService;

/**
 * 优惠券记录表
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/coupon")
public class CouponController {
	@Autowired
	private CouponService couponService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:coupon:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = couponService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:coupon:info")
	public R info(@PathVariable("id") Integer id) {
		CouponEntity coupon = couponService.getById(id);

		return R.ok().put("coupon", coupon);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:coupon:save")
	public R save(@RequestBody CouponEntity coupon) {
		couponService.save(coupon);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:coupon:update")
	public R update(@RequestBody CouponEntity coupon) {
		couponService.updateById(coupon);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:coupon:delete")
	public R delete(@RequestBody Integer[] ids) {
		couponService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
