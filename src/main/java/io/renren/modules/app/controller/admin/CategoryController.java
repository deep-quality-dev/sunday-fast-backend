package io.renren.modules.app.controller.admin;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.R;
import io.renren.modules.app.entity.CategoryEntity;
import io.renren.modules.app.service.CategoryService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("admin/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:category:list")
	public R list(CategoryEntity category) {
		List<CategoryEntity> categoryEntities = categoryService.list(Wrappers.<CategoryEntity>lambdaQuery().orderByAsc(CategoryEntity::getSort));
		return R.ok().put("data", categoryEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:category:info")
	public R info(@PathVariable("id") Integer id) {
		CategoryEntity category = categoryService.getById(id);
		return R.ok().put("category", category);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:category:save")
	public R save(@RequestBody CategoryEntity category) {
		categoryService.save(category);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:category:update")
	public R update(@RequestBody CategoryEntity category) {
		categoryService.updateById(category);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:category:delete")
	public R delete(@RequestBody Integer[] ids) {
		categoryService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
