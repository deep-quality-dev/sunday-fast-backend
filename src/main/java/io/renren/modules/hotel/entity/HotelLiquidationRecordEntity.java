package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:18
 */
@Data
@TableName("t_hotel_liquidation_record")
public class HotelLiquidationRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long liquidationId;
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 订单名称
	 */
	private String orderName;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 结算金额
	 */
	private BigDecimal amount;
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	/**
	 * 是否会员
	 */
	private Integer vipFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 费率
	 */
	private Float rate;

}
