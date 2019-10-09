package io.renren.modules.hotel.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StatisticsSeller {

	private int todayOrderNum;

	private BigDecimal todayMarketingAmount;

	private BigDecimal yesterdayMarketingAmount;

	private BigDecimal weekMarketingAmount;

	private int roomTotal;

	private int checkInNum;

	private StatisticsOrder statisticsOrder;
}
