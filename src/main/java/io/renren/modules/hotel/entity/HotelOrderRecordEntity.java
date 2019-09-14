package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-23 22:40:39
 */
@Data
@TableName("t_hotel_order_record")
public class HotelOrderRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private Long roomId;
	/**
	 * 
	 */
	private Long moneyId;
	/**
	 * 
	 */
	private Long priceId;
	/**
	 * 
	 */
	private String roomType;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Long orderId;

}
