package io.renren.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_cart")
public class CartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer uid;
	/**
	 * 商品id
	 */
	private Integer gid;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 购买数量
	 */
	private Integer num;
	/**
	 * 购物车选中状态
	 */
	private Integer selected;
	/**
	 *
	 */
	private String specKey;
	/**
	 *
	 */
	private String specKeyValue;

}
