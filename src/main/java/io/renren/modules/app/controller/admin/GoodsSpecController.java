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
import io.renren.modules.app.entity.GoodsSpecEntity;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.service.GoodsSpecService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/goodsspec")
public class GoodsSpecController {
	@Autowired
	private GoodsSpecService goodsSpecService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:goodsspec:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = goodsSpecService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:goodsspec:info")
	public R info(@PathVariable("id") Integer id) {
		GoodsSpecEntity goodsSpec = goodsSpecService.getById(id);

		return R.ok().put("goodsSpec", goodsSpec);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:goodsspec:save")
	public R save(@RequestBody SpecForm specForm) {
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:goodsspec:update")
	public R update(@RequestBody GoodsSpecEntity goodsSpec) {
		goodsSpecService.updateById(goodsSpec);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:goodsspec:delete")
	public R delete(@RequestBody Integer[] ids) {
		goodsSpecService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
