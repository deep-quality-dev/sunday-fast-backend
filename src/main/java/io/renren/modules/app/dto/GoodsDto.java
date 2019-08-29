package io.renren.modules.app.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GoodsDto {

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
	 * 商品状态(10上架 20下架)
	 */
	private Integer goodsStatus;

	/**
	 * 销量
	 */
	private Integer sales;
	/**
	 * 商品排序
	 */
	private Integer sort;

	/**
	 * 商品图片
	 */
	private String img;

	/**
	 * 添加时间
	 */
	private Date createTime;

}
