/**
 * @Auther: taoz
 * @Date: 17/06/2019 12:19
 * @Description:
 */
package io.renren.modules.app.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.form.SpecForm;
import lombok.Data;

/**
 * @author taoz
 */
@Data
public class GoodsDetailVo {

	private Integer id;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 分类
	 */
	private String categoryName;

	/**
	 * 销量
	 */
	private Integer sales;

	/**
	 * 商品图片
	 */
	private String img;

	/**
	 * 商品规格 单规格1 多规格2
	 */
	private Integer specType;

	/**
	 * 商品描述
	 */
	private String content;

	/**
	 * 商品价格
	 */
	private BigDecimal price;

	/**
	 * 商品图片集合
	 */
	private List<String> goodsImages = new ArrayList<>();

	/**
	 * 规格列表
	 */
	List<SpecForm> specs = new ArrayList<SpecForm>();

	/**
	 * 商品sku列表
	 */
	private List<GoodsSku> goodsSkus = new ArrayList<GoodsSku>();
}
