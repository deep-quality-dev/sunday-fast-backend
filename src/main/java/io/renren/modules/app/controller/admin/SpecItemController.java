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
import io.renren.modules.app.entity.SpecItemEntity;
import io.renren.modules.app.service.SpecItemService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-23 13:59:07
 */
@RestController
@RequestMapping("admin/specitem")
public class SpecItemController {
	@Autowired
	private SpecItemService specItemService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:specitem:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = specItemService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:specitem:info")
	public R info(@PathVariable("id") Integer id) {
		SpecItemEntity specItem = specItemService.getById(id);
		return R.ok().put("specItem", specItem);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:specitem:save")
	public R save(@RequestBody SpecItemEntity specItem) {
		specItemService.save(specItem);
		return R.ok().put("data", specItem);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:specitem:update")
	public R update(@RequestBody SpecItemEntity specItem) {
		specItemService.updateById(specItem);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:specitem:delete")
	public R delete(@RequestBody Integer[] ids) {
		specItemService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
