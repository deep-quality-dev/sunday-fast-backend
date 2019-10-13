package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.modules.constants.HotelOrderStatus;
import io.renren.modules.hotel.dao.HotelMemberDao;
import io.renren.modules.hotel.dao.HotelOrderDao;
import io.renren.modules.hotel.dao.HotelRoomDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.service.HotelHotelStatisticsService;
import io.renren.modules.hotel.vo.StatisticsApply;
import io.renren.modules.hotel.vo.StatisticsMember;
import io.renren.modules.hotel.vo.StatisticsOrder;
import io.renren.modules.hotel.vo.StatisticsPlatfrom;
import io.renren.modules.hotel.vo.StatisticsSeller;

@Service
public class HotelHotelStatisticsServiceImpl implements HotelHotelStatisticsService {

	@Autowired
	private HotelSellerDao hotelSellerDao;

	@Autowired
	private HotelOrderDao hotelOrderDao;

	@Autowired
	private HotelMemberDao hotelMemberDao;

	@Autowired
	private HotelRoomDao hotelRoomDao;

	@Override
	public StatisticsPlatfrom platfrom(List<String> dates) {

		StatisticsPlatfrom statisticsPlatfrom = new StatisticsPlatfrom();
		// 入驻数据
		System.out.println(CollectionUtil.isNotEmpty(dates));
		StatisticsApply statisticsApply = new StatisticsApply();
		int applyNum = hotelSellerDao.selectCount(Wrappers.<HotelSellerEntity>lambdaQuery().eq(HotelSellerEntity::getState, 1).between(CollectionUtil.isNotEmpty(dates), HotelSellerEntity::getTime, DateUtil.parse(dates.get(0)).getTime(), DateUtil.parse(dates.get(1)).getTime()));
		int passNum = hotelSellerDao.selectCount(Wrappers.<HotelSellerEntity>lambdaQuery().eq(HotelSellerEntity::getState, 2).between(CollectionUtil.isNotEmpty(dates), HotelSellerEntity::getTime, DateUtil.parse(dates.get(0)).getTime(), DateUtil.parse(dates.get(1)).getTime()));
		int refuseNum = hotelSellerDao.selectCount(Wrappers.<HotelSellerEntity>lambdaQuery().eq(HotelSellerEntity::getState, 3).between(CollectionUtil.isNotEmpty(dates), HotelSellerEntity::getTime, DateUtil.parse(dates.get(0)).getTime(), DateUtil.parse(dates.get(1)).getTime()));
		statisticsApply.setApplyNum(applyNum);
		statisticsApply.setPassNum(passNum);
		statisticsApply.setRefuseNum(refuseNum);
		statisticsApply.setTotal(applyNum + passNum + refuseNum);
		statisticsPlatfrom.setStatisticsApply(statisticsApply);

		// 订单数据
		StatisticsOrder statisticsOrder = new StatisticsOrder();
		int cancelNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, 3).between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		int checkInNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, 5).between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		int completeNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, 4).between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		int refundNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, 7).between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		int total = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		statisticsOrder.setCancelNum(cancelNum);
		statisticsOrder.setCheckInNum(checkInNum);
		statisticsOrder.setCompleteNum(completeNum);
		statisticsOrder.setRefundNum(refundNum);
		statisticsOrder.setTotal(total);
		statisticsPlatfrom.setStatisticsOrder(statisticsOrder);

		// 有效订单
		int validOrderNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().notIn(HotelOrderEntity::getStatus, HotelOrderStatus.CANCEL));
		statisticsPlatfrom.setValidOrderNum(validOrderNum);

		// 无效订单
		int invalidOrderNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().in(HotelOrderEntity::getStatus, HotelOrderStatus.CANCEL));
		statisticsPlatfrom.setInvalidOrderNum(invalidOrderNum);

		// 营业额
		List<HotelOrderEntity> hotelOrderEntities = hotelOrderDao.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().in(HotelOrderEntity::getStatus, Arrays.asList(HotelOrderStatus.PAYED, HotelOrderStatus.CHECK_IN, HotelOrderStatus.COMPLETE)).between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		BigDecimal totalCost = new BigDecimal(0);
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			totalCost = NumberUtil.add(totalCost, hotelOrderEntity.getTotalCost());
		}
		statisticsPlatfrom.setMarketingAmount(totalCost);
		// 退款
		hotelOrderEntities = hotelOrderDao.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().in(HotelOrderEntity::getStatus, Arrays.asList(HotelOrderStatus.REFUNDED, HotelOrderStatus.REFUSE_REFUND)).between(CollectionUtil.isNotEmpty(dates), HotelOrderEntity::getCreateTime, DateUtil.parse(dates.get(0)), DateUtil.parse(dates.get(1))));
		BigDecimal refundAmount = new BigDecimal(0);
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			refundAmount = NumberUtil.add(refundAmount, hotelOrderEntity.getTotalCost());
		}
		statisticsPlatfrom.setRefundAmount(refundAmount);

		// 会员数据
		StatisticsMember statisticsMember = new StatisticsMember();
		int membertotal = hotelMemberDao.selectCount(Wrappers.<HotelMemberEntity>lambdaQuery());
		int todayNum = hotelMemberDao.selectCount(Wrappers.<HotelMemberEntity>lambdaQuery().between(HotelMemberEntity::getJoinTime, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date())));
		int yesterdayNum = hotelMemberDao.selectCount(Wrappers.<HotelMemberEntity>lambdaQuery().between(HotelMemberEntity::getJoinTime, DateUtil.beginOfDay(DateUtil.yesterday()), DateUtil.endOfDay(DateUtil.yesterday())));
		int monthNum = hotelMemberDao.selectCount(Wrappers.<HotelMemberEntity>lambdaQuery().between(HotelMemberEntity::getJoinTime, DateUtil.offsetDay(DateUtil.date(), -30), DateUtil.endOfDay(DateUtil.date())));
		statisticsMember.setMonthNum(monthNum);
		statisticsMember.setTodayNum(todayNum);
		statisticsMember.setTotal(membertotal);
		statisticsMember.setYesterdayNum(yesterdayNum);
		statisticsPlatfrom.setStatisticsMember(statisticsMember);

		// 今日新增酒店
		int hotelCreateNum = hotelSellerDao.selectCount(Wrappers.<HotelSellerEntity>lambdaQuery().eq(HotelSellerEntity::getState, 2).between(HotelSellerEntity::getTime, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date())));
		// 酒店总数
		int hotelTotal = hotelSellerDao.selectCount(Wrappers.<HotelSellerEntity>lambdaQuery().eq(HotelSellerEntity::getState, 2));
		statisticsPlatfrom.setHotelCreateNum(hotelCreateNum);
		statisticsPlatfrom.setHotelTotal(hotelTotal);
		return statisticsPlatfrom;
	}

	@Override
	public StatisticsSeller seller(Long sellerId) {
		StatisticsSeller statisticsSeller = new StatisticsSeller();
		// 订单数据
		StatisticsOrder statisticsOrder = new StatisticsOrder();
		int cancelNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getStatus, 3));
		int checkInNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getStatus, 5));
		int completeNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getStatus, 4));
		int refundNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getStatus, 7));
		int total = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId));
		statisticsOrder.setCancelNum(cancelNum);
		statisticsOrder.setCheckInNum(checkInNum);
		statisticsOrder.setCompleteNum(completeNum);
		statisticsOrder.setRefundNum(refundNum);
		statisticsOrder.setTotal(total);
		statisticsSeller.setStatisticsOrder(statisticsOrder);

		// 今日订单
		int todayOrderNum = hotelOrderDao.selectCount(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getStatus, 3).between(HotelOrderEntity::getCreateTime, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date())));
		statisticsSeller.setTodayOrderNum(todayOrderNum);

		// 今日营业额
		List<HotelOrderEntity> hotelOrderEntities = hotelOrderDao.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).in(HotelOrderEntity::getStatus, Arrays.asList(HotelOrderStatus.PAYED, HotelOrderStatus.CHECK_IN, HotelOrderStatus.COMPLETE)).between(HotelOrderEntity::getCreateTime, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date())));
		BigDecimal todayMarketingAmount = new BigDecimal(0);
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			todayMarketingAmount = NumberUtil.add(todayMarketingAmount, hotelOrderEntity.getTotalCost());
		}
		statisticsSeller.setTodayMarketingAmount(todayMarketingAmount);

		// 昨日营业额
		hotelOrderEntities = hotelOrderDao.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).in(HotelOrderEntity::getStatus, Arrays.asList(HotelOrderStatus.PAYED, HotelOrderStatus.CHECK_IN, HotelOrderStatus.COMPLETE)).between(HotelOrderEntity::getCreateTime, DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(), -1)), DateUtil.endOfDay(DateUtil.offsetDay(DateUtil.date(), -1))));
		BigDecimal yesterdayMarketingAmount = new BigDecimal(0);
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			yesterdayMarketingAmount = NumberUtil.add(yesterdayMarketingAmount, hotelOrderEntity.getTotalCost());
		}
		statisticsSeller.setYesterdayMarketingAmount(yesterdayMarketingAmount);
		// 7day
		hotelOrderEntities = hotelOrderDao.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).in(HotelOrderEntity::getStatus, Arrays.asList(HotelOrderStatus.PAYED, HotelOrderStatus.CHECK_IN, HotelOrderStatus.COMPLETE)).between(HotelOrderEntity::getCreateTime, DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(), -7)), DateUtil.endOfDay(DateUtil.offsetDay(DateUtil.date(), -1))));
		BigDecimal weekMarketingAmount = new BigDecimal(0);
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			weekMarketingAmount = NumberUtil.add(weekMarketingAmount, hotelOrderEntity.getTotalCost());
		}
		statisticsSeller.setWeekMarketingAmount(weekMarketingAmount);

		// 房型总数
		int roomTotal = hotelRoomDao.selectCount(Wrappers.<HotelRoomEntity>lambdaQuery().eq(HotelRoomEntity::getSellerId, sellerId));
		statisticsSeller.setRoomTotal(roomTotal);

		int checkInRoomNum = 0;
		hotelOrderEntities = hotelOrderDao.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getStatus, HotelOrderStatus.CHECK_IN).between(HotelOrderEntity::getCreateTime, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date())));
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			checkInRoomNum = NumberUtil.add(checkInRoomNum, NumberUtil.div(hotelOrderEntity.getNum(), hotelOrderEntity.getDays())).intValue();
		}
		statisticsSeller.setCheckInNum(checkInNum);

		return statisticsSeller;
	}

}
