package io.renren.modules.app.constans;

public interface CommonConstants {

	/**
	 * 商品状态(10上架 20下架
	 */
	public static final Integer GOODS_STATUS_PUTAWAY = 10;
	public static final Integer GOODS_STATUS_SOLD_OUT = 20;
	/**
	 * 商品规格 单规格1 多规格2
	 */
	public static final Integer SPEC_TYPE_SINGLETON = 1;
	public static final Integer SPEC_TYPE_MULTITON = 2;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "sunday";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;
	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 有效数据状态
	 */
	public static final int ENABLED = 1;
}
