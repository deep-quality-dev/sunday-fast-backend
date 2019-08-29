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
import io.renren.modules.app.entity.SpecEntity;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.service.SpecService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("admin/spec")
public class SpecController {
	@Autowired
	private SpecService specService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:spec:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = specService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:spec:info")
	public R info(@PathVariable("id") Integer id) {
		SpecEntity spec = specService.getById(id);

		return R.ok().put("spec", spec);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:spec:save")
	public R save(@RequestBody SpecForm spec) {
		SpecForm specForm = specService.saveSpec(spec);
		return R.ok().put("data", specForm);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:spec:update")
	public R update(@RequestBody SpecEntity spec) {
		specService.updateById(spec);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:spec:delete")
	public R delete(@RequestBody Integer[] ids) {
		specService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
