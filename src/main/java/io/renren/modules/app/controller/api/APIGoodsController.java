/**
 * @Auther: taoz
 * @Date: 14/06/2019 12:09
 * @Description:
 */
package io.renren.modules.app.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.vo.GoodsDetailVo;
import io.renren.modules.app.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "积分商城接口", tags = { "积分商城接口" })
@RestController
@RequestMapping("app/goods")
public class APIGoodsController {

	@Autowired
	private GoodsService goodsService;

	/**
	 * 商品列表
	 *
	 * @return
	 */
	@ApiOperation("商品列表")
	@GetMapping("/list")
	public R list(@RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit, GoodsDto params) {
		Page<GoodsVo> goodsListPage = goodsService.goodsList(new Page<GoodsVo>(page, limit), params);
		return R.ok().put("data", goodsListPage);
	}

	/**
	 *
	 * @param goodsId
	 * @return
	 */
	@ApiOperation("商品详情")
	@GetMapping("/detail")
	public R detail(@RequestParam(name = "id") Integer goodsId) {
		GoodsDetailVo goodsDetail = goodsService.goodsDetail(goodsId, null);
		return R.ok().put("data", goodsDetail);
	}
}
