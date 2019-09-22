/**
 * @Auther: taoz
 * @Date: 14/06/2019 12:09
 * @Description:
 */
package io.renren.modules.app.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.entity.RegionEntity;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.service.RegionService;
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

	@Autowired
	private RegionService regionService;

	@GetMapping("region")
	public R region() {
		List<Map<String, Object>> data = new ArrayList<>();
		List<RegionEntity> l1 = regionService.list(Wrappers.<RegionEntity>lambdaQuery().eq(RegionEntity::getPid, 0));
		for (RegionEntity region1 : l1) {
			Map<String, Object> lv1Map = new HashedMap();
			lv1Map.put("code", region1.getId());
			lv1Map.put("name", region1.getName());
			List<Map<String, Object>> cityMap = new ArrayList<>();

			List<RegionEntity> l2 = regionService.list(Wrappers.<RegionEntity>lambdaQuery().eq(RegionEntity::getPid, region1.getId()));
			for (RegionEntity region2 : l2) {
				Map<String, Object> lv2Map = new HashedMap();
				lv2Map.put("code", region2.getId());
				lv2Map.put("name", region2.getName());

				List<Map<String, Object>> areas = new ArrayList<>();
				List<RegionEntity> l3 = regionService.list(Wrappers.<RegionEntity>lambdaQuery().eq(RegionEntity::getPid, region2.getId()));
				for (RegionEntity region3 : l3) {
					Map<String, Object> lv3Map = new HashedMap();
					lv3Map.put("code", region3.getId());
					lv3Map.put("name", region3.getName());
					areas.add(lv3Map);
				}
				lv2Map.put("areas", areas);
				cityMap.add(lv2Map);
			}
			lv1Map.put("cities", cityMap);
			data.add(lv1Map);
		}
		return R.ok().put("regions", data);
	}

	//////
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
