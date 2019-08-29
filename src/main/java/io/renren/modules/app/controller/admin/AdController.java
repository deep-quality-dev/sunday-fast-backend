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
import io.renren.modules.app.entity.AdEntity;
import io.renren.modules.app.service.AdService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/ad")
public class AdController {
	@Autowired
	private AdService adService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:ad:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = adService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:ad:info")
	public R info(@PathVariable("id") Integer id) {
		AdEntity ad = adService.getById(id);

		return R.ok().put("ad", ad);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:ad:save")
	public R save(@RequestBody AdEntity ad) {
		adService.save(ad);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:ad:update")
	public R update(@RequestBody AdEntity ad) {
		adService.updateById(ad);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:ad:delete")
	public R delete(@RequestBody Integer[] ids) {
		adService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
