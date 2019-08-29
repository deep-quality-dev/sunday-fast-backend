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
import io.renren.modules.app.entity.DistributeEntity;
import io.renren.modules.app.service.DistributeService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/distribute")
public class DistributeController {
	@Autowired
	private DistributeService distributeService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:distribute:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = distributeService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:distribute:info")
	public R info(@PathVariable("id") Integer id) {
		DistributeEntity distribute = distributeService.getById(id);

		return R.ok().put("distribute", distribute);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:distribute:save")
	public R save(@RequestBody DistributeEntity distribute) {
		distributeService.save(distribute);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:distribute:update")
	public R update(@RequestBody DistributeEntity distribute) {
		distributeService.updateById(distribute);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:distribute:delete")
	public R delete(@RequestBody Integer[] ids) {
		distributeService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
