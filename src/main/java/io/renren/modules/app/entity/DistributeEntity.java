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
@TableName("t_distribute")
public class DistributeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * 用户ID
	 */
	private Integer uid;
	/**
	 * 买家ID
	 */
	private Integer buyerId;
	/**
	 * 佣金
	 */
	private BigDecimal money;
	/**
	 * 获佣时间
	 */
	private Date moneyTime;
	/**
	 * 佣金状态 0未付款 1已冻结 2解除冻结
	 */
	private Integer state;

}
