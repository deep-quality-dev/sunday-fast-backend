package io.renren.modules.app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderGoodsDto {

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
