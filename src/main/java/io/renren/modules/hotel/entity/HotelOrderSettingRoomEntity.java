package io.renren.modules.hotel.entity;

import java.io.Serializable;

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
@TableName("t_hotel_order_setting_room")
public class HotelOrderSettingRoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Integer roomId;
	/**
	 * 
	 */
	private Long settingId;

	/**
	 * 数量
	 */
	private int num;

}
