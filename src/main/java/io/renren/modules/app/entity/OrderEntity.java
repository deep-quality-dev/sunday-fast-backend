package io.renren.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId
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

}
