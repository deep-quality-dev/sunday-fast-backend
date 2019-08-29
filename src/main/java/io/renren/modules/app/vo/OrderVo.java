package io.renren.modules.app.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author TAOZ
 *
 */
@Data
public class OrderVo {
	/**
	 * 订单id
	 */
	private Integer id;
	/**
	 * 订单号，小程序中显示用
	 */
	private String orderSn;
	
	/**
	 * 订单状态 待付款0 已完成1 待发货2 待收货3 已取消4
	 */
	private Integer orderStatus;

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
	
	List<OrderItemVo> orderItem = new ArrayList<OrderItemVo>();
}
