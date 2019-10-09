package io.renren.modules.hotel.service;

import java.util.List;

import io.renren.modules.hotel.vo.StatisticsPlatfrom;
import io.renren.modules.hotel.vo.StatisticsSeller;

public interface HotelHotelStatisticsService {

	/**
	 * 平台统计
	 * 
	 * @param params
	 * @return
	 */
	StatisticsPlatfrom platfrom(List<String> dates);

	/**
	 * 商家数据概览
	 * 
	 * @return
	 */
	StatisticsSeller seller(Long sellerId);

}
