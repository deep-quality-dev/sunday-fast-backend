package io.renren.modules.hotel.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.hotel.service.HotelHotelStatisticsService;
import io.renren.modules.hotel.vo.StatisticsPlatfrom;
import io.renren.modules.hotel.vo.StatisticsSeller;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 统计相关接口
 * 
 * @author taoz
 *
 */
@RestController
@RequestMapping("hotel/statistics")
public class HotelStatisticsController extends AbstractController {

	@Autowired
	private HotelHotelStatisticsService hotelHotelStatisticsService;

	/**
	 * 平台统计
	 */
	@RequestMapping("/platfrom")
	@RequiresPermissions("hotel:statistics:platfrom")
	public R platfrom(@RequestParam List<String> dates) {
		StatisticsPlatfrom statisticsPlatfrom = hotelHotelStatisticsService.platfrom(dates);
		return R.ok(statisticsPlatfrom);
	}

	/**
	 * 平台商家统计
	 */
	@RequestMapping("/seller")
	@RequiresPermissions("hotel:statistics:seller")
	public R seller() {
		StatisticsSeller statisticsSeller = hotelHotelStatisticsService.seller(isAdmin() ? 0L : getSellerId());
		return R.ok(statisticsSeller);
	}

}
