package io.renren.modules.app.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemVo {

	/**
	 *
	 */
	private Integer id;
	/**
	 * 订单id
	 */
	private Integer oid;
	/**
	 * 商品id
	 */
	private Integer gid;
	/**
	 * 购买数量
	 */
	private Integer num;
	
	private String img;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 *
	 */
	private String specKey;
	/**
	 *
	 */
	private String specKeyValue;
}
