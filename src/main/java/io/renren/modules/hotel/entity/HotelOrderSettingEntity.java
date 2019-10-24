package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 19:16:27
 */
@Data
@TableName("t_hotel_order_setting")
public class HotelOrderSettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Integer autoOrder;
	/**
	 * 
	 */
	private Integer custSetting;
	/**
	 * 
	 */
	private Date createTime;
	
	private Long sellerId;

	@TableField(exist = false)
	private List<HotelOrderSettingRoomEntity> rooms;

	@TableField(exist = false)
	private List<HotelOrderSettingDateEntity> days;

}
