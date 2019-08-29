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
@TableName("t_order_goods")
public class OrderGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
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
	/**
	 * 是否评价 否0 是1
	 */
	private Integer commentStatus;
	/**
	 *
	 */
	private Date submitTime;
	
	private String img;

}
