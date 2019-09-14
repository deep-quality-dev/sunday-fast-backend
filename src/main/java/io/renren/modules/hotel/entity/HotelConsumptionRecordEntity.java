package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 会员卡消费明细
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-28 22:24:24
 */
@Data
@TableName("t_hotel_consumption_record")
public class HotelConsumptionRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 卡片ID
	 */
	private Long cardId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 日期
	 */
	private Date createDate;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 类型，备用字段
	 */
	private Integer type;

}
