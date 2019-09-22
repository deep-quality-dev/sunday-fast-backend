package io.renren.modules.hotel.vo;

import lombok.Data;

/**
 * 订单明细
 * 
 * @author taoz
 *
 */
@Data
public class OrderDetail {

	private int num = 1;

	private String amount;

	private String discounts;

	private String date;
}
