package io.renren.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-23 15:54:23
 */
@Data
@TableName("t_goods_spec_rel")
public class GoodsSpecRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 商品id
	 */
	private Integer goodsId;
	/**
	 * 规格组id
	 */
	private Integer specId;
	/**
	 * 规格值id
	 */
	private Integer specValueId;
	/**
	 * 创建时间
	 */
	private Integer createTime;

}
