package io.renren.modules.hotel.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.form.SellerApplyForm;
import io.renren.modules.hotel.service.HotelBrandService;
import io.renren.modules.hotel.service.HotelMemberLevelService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.HotelBrandTypeVo;
import io.renren.modules.hotel.vo.HotelInfo;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import io.renren.modules.hotel.vo.HotelSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 酒店商家
 *
 * @author taoz
 */
@Api(value = "酒店商家接口", tags = { "酒店商家接口" })
@RestController
@RequestMapping("/hotel/seller")
public class HotelSellerAPI extends BaseController {

	@Autowired
	private HotelSellerService hotelSellerService;

	@Autowired
	private HotelBrandService hotelBrandService;

	@Autowired
	private HotelMemberLevelService hotelMemberLevelService;

	@Login
	@ApiOperation("是否本店会员")
	@GetMapping("/memberFlag/{sellerId}")
	public R memberFlag(@RequestAttribute("userId") Long userId, @PathVariable Long sellerId) {
		boolean result = hotelMemberLevelService.checkVipStatus(userId, sellerId);
		return R.ok().put("data", result);
	}

	@ApiOperation("酒店预定提醒")
	@GetMapping("/reserveRemind")
	public R reserveRemind(Long sellerId) {
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(sellerId);
		Map<String, String> data = new HashMap<String, String>();
		data.put("reserveRemind", hotelSellerEntity.getReserveRemind());
		return R.ok(data);
	}

	@ApiOperation("酒店过滤条件")
	@GetMapping("/filter")
	public R filter() {
		Map<String, Object> data = hotelSellerService.filterData();
		return R.ok(data);
	}

	/**
	 * 酒店信息
	 *
	 * @param appId
	 */
	@Login
	@ApiOperation("酒店信息")
	@GetMapping("/info/{sellerId}")
	public R info(@RequestAttribute("userId") Long userId, @PathVariable Long sellerId) {
		HotelInfo hotelInfo = hotelSellerService.sellerInfo(userId, sellerId);
		return R.ok(hotelInfo);
	}

	/**
	 * 酒店列表
	 *
	 * @param page
	 * @param params
	 * @return
	 */
	@ApiOperation("酒店列表")
	@PostMapping("/page")
	@Login
	public R page(@RequestAttribute("userId") Long userId, @RequestBody HotelSearchCondition params, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
		Page<HotelItemVo> pageResult = hotelSellerService.hotelPage(userId, params, new Page<HotelItemVo>(params.getPage(), params.getLimit()));
		return R.ok(pageResult);
	}

	/**
	 * 酒店搜索
	 * 
	 * @param kw
	 * @param page
	 * @param limit
	 * @return
	 */
	@ApiOperation("酒店列表--办卡页面")
	@GetMapping("/search")
	public R search(String kw, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
		Page<HotelSearchVo> pageResult = hotelSellerService.search(kw, new Page<HotelSearchVo>(page, limit));
		return R.ok(pageResult);
	}

	/**
	 * 商家入驻申请
	 * 
	 * @return
	 */
	@ApiOperation("商家入驻申请")
	@PostMapping("/sellerApply")
	public R sellerApply(@RequestBody SellerApplyForm sellerApplyForm) {
		hotelSellerService.sellerApply(sellerApplyForm);
		return R.ok();
	}

	@ApiOperation("酒店品牌列表")
	@GetMapping("/hotelBrands")
	public R hotelBrands() {
		List<HotelBrandTypeVo> brands = hotelBrandService.hotelBrandWithType();
		return R.ok(brands);
	}
}
