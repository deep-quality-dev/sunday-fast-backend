package io.renren.modules.hotel.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CardConsumptionVo {

	private String amount;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 日期
	 */
	private Date createDate;
}
