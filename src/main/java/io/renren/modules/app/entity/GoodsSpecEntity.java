package io.renren.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-23 15:40:51
 */
@Data
@TableName("t_goods_spec")
public class GoodsSpecEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商品ID
	 */
	private Integer gid;
	/**
	 * 规格ID组合
	 */
	private String skuKey;
	/**
	 * 规格项组合
	 */
	private String skuKeyValue;
	/**
	 * 
	 */
	private BigDecimal linePrice;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 库存
	 */
	private Integer store;
	/**
	 * 重量(kg)
	 */
	private Double weight;
	/**
	 * 图片
	 */
	private String img;

}
