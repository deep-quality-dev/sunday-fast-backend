package io.renren.modules.app.controller.admin;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.form.EditGoodsForm;
import io.renren.modules.app.service.GoodsService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("admin/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:goods:list")
	public R list(Page page, GoodsDto goodsDto) {
		return R.ok().put("data", goodsService.getGoodsPage(page, goodsDto));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:goods:info")
	public R info(@PathVariable("id") Integer id) {
		EditGoodsForm goods = goodsService.goodsInfo(id);
		return R.ok().put("data", goods);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:goods:save")
	public R save(@RequestBody EditGoodsForm goods) {
		goodsService.addGoods(goods);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:goods:update")
	public R update(@RequestBody EditGoodsForm goods) {
		goodsService.updateGoods(goods);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:goods:delete")
	public R delete(@RequestBody Integer[] ids) {
		goodsService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

}
