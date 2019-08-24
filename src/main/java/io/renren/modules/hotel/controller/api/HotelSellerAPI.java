package io.renren.modules.hotel.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.HotelInfo;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 酒店商家
 *
 * @author taoz
 */
@Api(value = "酒店商家接口", tags = { "酒店商家接口" })
@RestController
@RequestMapping("/{appId}/hotel/seller")
public class HotelSellerAPI extends BaseController {

	@Autowired
	private HotelSellerService hotelSellerService;

	/**
	 * 酒店信息
	 *
	 * @param appId
	 */
	@ApiOperation("酒店信息")
	@GetMapping("/info{sellerId}")
	public R info(@PathVariable Long sellerId) {
		HotelInfo hotelInfo = hotelSellerService.sellerInfo(sellerId);
		return R.ok().put("data", hotelInfo);
	}

	/**
	 * 酒店列表
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	@ApiOperation("酒店列表")
	@GetMapping("/page")
	public R page(@ModelAttribute HotelSearchCondition params, Page page) {
		Page<HotelItemVo> pageResult = hotelSellerService.hotelPage(1L, params, page);
		return R.ok().put("data", pageResult);
	}
}
