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
import io.renren.modules.app.entity.MoneyApplyEntity;
import io.renren.modules.app.service.MoneyApplyService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/moneyapply")
public class MoneyApplyController {
	@Autowired
	private MoneyApplyService moneyApplyService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:moneyapply:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = moneyApplyService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:moneyapply:info")
	public R info(@PathVariable("id") Integer id) {
		MoneyApplyEntity moneyApply = moneyApplyService.getById(id);

		return R.ok().put("moneyApply", moneyApply);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:moneyapply:save")
	public R save(@RequestBody MoneyApplyEntity moneyApply) {
		moneyApplyService.save(moneyApply);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:moneyapply:update")
	public R update(@RequestBody MoneyApplyEntity moneyApply) {
		moneyApplyService.updateById(moneyApply);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:moneyapply:delete")
	public R delete(@RequestBody Integer[] ids) {
		moneyApplyService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
