package io.renren.modules.app.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {

	private Integer id;
	/**
	 * 订单号，小程序中显示用
	 */
	private String orderSn;
	/**
	 * 订单号，微信支付时提交用，可变
	 */
	private String orderSnSubmit;
	/**
	 * 用户id
	 */
	private Integer uid;
	/**
	 * 订单状态 待付款0 已完成1 待发货2 待收货3 已取消4
	 */
	private Integer orderStatus;
	/**
	 * 物流单号
	 */
	private String expressnum;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 订单总价
	 */
	private BigDecimal amount;
	/**
	 * 下单时间
	 */
	private Date submitTime;

	private List<OrderGoodsDto> orderGoods = new ArrayList<OrderGoodsDto>();
}
