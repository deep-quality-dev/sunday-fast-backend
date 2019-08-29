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
import io.renren.modules.app.entity.GoodsCollectEntity;
import io.renren.modules.app.service.GoodsCollectService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/goodscollect")
public class GoodsCollectController {
	@Autowired
	private GoodsCollectService goodsCollectService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:goodscollect:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = goodsCollectService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:goodscollect:info")
	public R info(@PathVariable("id") Integer id) {
		GoodsCollectEntity goodsCollect = goodsCollectService.getById(id);

		return R.ok().put("goodsCollect", goodsCollect);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:goodscollect:save")
	public R save(@RequestBody GoodsCollectEntity goodsCollect) {
		goodsCollectService.save(goodsCollect);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:goodscollect:update")
	public R update(@RequestBody GoodsCollectEntity goodsCollect) {
		goodsCollectService.updateById(goodsCollect);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:goodscollect:delete")
	public R delete(@RequestBody Integer[] ids) {
		goodsCollectService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
