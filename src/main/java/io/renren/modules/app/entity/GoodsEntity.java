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
@TableName("t_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
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
	 * 浏览量
	 */
	private Integer views;
	/**
	 * 库存数量
	 */
	private Integer store;
	/**
	 * 收藏数
	 */
	private Integer collect;
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
	private String img;
	/**
	 * 销量
	 */
	private Integer sales;
	/**
	 * 商品排序
	 */
	private Integer sort;

	/**
	 * 添加时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 数据状态
	 */
	private int enabled;

}
