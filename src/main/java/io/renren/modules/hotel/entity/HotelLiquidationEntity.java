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
 * @date 2019-10-23 17:42:19
 */
@Data
@TableName("t_hotel_liquidation")
public class HotelLiquidationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 周期
	 */
	private String period;
	/**
	 * 商家名称
	 */
	private String sellerName;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 结算金额
	 */
	private BigDecimal amount;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
