package io.renren.modules.hotel.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 平台统计总览
 * 
 * @author taoz
 *
 */
@Data
public class StatisticsPlatfrom {

	private StatisticsApply statisticsApply;

	private StatisticsOrder statisticsOrder;

	private BigDecimal marketingAmount;

	private BigDecimal refundAmount;

	private StatisticsMember statisticsMember;

	private int hotelTotal;

	private int hotelCreateNum;

	private int validOrderNum;

	private int invalidOrderNum;

}
