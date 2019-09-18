package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-18 21:58:26
 */
@Data
@TableName("t_hotel_member_level_pay")
public class HotelMemberLevelPayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String outTradeNo;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private Long levelId;
	/**
	 * 
	 */
	private String detail;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private int status =  0;

}
