package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 结算设置
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:19
 */
@Data
@TableName("t_hotel_liquidation_setting")
public class HotelLiquidationSettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 周期类型
	 */
	private int type;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 会员订单费率
	 */
	@NotNull(message = "会员订单费率不能为空")
	private BigDecimal vipRate;
	/**
	 * 普通费率
	 */
	@NotNull(message = "普通费率不能为空")
	private BigDecimal rate;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
