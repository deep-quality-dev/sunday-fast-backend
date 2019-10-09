package io.renren.modules.hotel.vo;

import lombok.Data;

@Data
public class StatisticsOrder {

	private int total;

	private int cancelNum;

	private int checkInNum;

	private int completeNum;

	private int refundNum;
}
