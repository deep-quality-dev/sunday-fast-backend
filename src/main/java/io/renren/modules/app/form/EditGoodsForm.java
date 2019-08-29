package io.renren.modules.app.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author Administrator
 *
 */
@Data
public class EditGoodsForm {

	/**
	 * 商品id
	 */
	private Integer id;
	/**
	 * 分类
	 */
	private Integer categoryId;

	/**
	 * 商品状态(10上架 20下架
	 */
	private Integer goodsStatus;

	/**
	 * 配送模板
	 */
	private Integer deliveryId;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 库存数量
	 */
	private Integer store;

	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 重量(kg)
	 */
	private BigDecimal weight;
	/**
	 * 商品规格 单规格1 多规格2
	 */
	private Integer specType;
	/**
	 * 商品描述
	 */
	private String content;
	/**
	 * 商品图片
	 */
	private List<String> imgs;

	/**
	 * 商品排序
	 */
	private Integer sort;

	/**
	 * 规格列表
	 */
	List<SpecForm> specs = new ArrayList<SpecForm>();

	/**
	 * sku 列表
	 */
	private List<GoodsSku> goodsSkus = new ArrayList<GoodsSku>();

}
