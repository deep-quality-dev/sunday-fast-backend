package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 用户收藏表
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 21:28:42
 */
@Data
@TableName("t_hotel_member_collect")
public class HotelMemberCollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 类型，1-酒店
	 */
	private Integer bizType;
	/**
	 * ID
	 */
	private Long bizId;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 用户ID
	 */
	private Long userId;

}
