package io.renren.modules.app.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GoodsSku {

	private Long id;

	private Integer goodsId;

	private String goodsNo;

	private String skuImg;

	private String goodsSkuKey;

	private String goodsSkuValue;

	private int stock;

	private Double weight;

	private BigDecimal linePrice;

	private BigDecimal sellPrice;
}
