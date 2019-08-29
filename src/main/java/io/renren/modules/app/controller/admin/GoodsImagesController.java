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
import io.renren.modules.app.entity.GoodsImagesEntity;
import io.renren.modules.app.service.GoodsImagesService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/goodsimages")
public class GoodsImagesController {
	@Autowired
	private GoodsImagesService goodsImagesService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:goodsimages:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = goodsImagesService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:goodsimages:info")
	public R info(@PathVariable("id") Integer id) {
		GoodsImagesEntity goodsImages = goodsImagesService.getById(id);

		return R.ok().put("goodsImages", goodsImages);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:goodsimages:save")
	public R save(@RequestBody GoodsImagesEntity goodsImages) {
		goodsImagesService.save(goodsImages);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:goodsimages:update")
	public R update(@RequestBody GoodsImagesEntity goodsImages) {
		goodsImagesService.updateById(goodsImages);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:goodsimages:delete")
	public R delete(@RequestBody Integer[] ids) {
		goodsImagesService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
