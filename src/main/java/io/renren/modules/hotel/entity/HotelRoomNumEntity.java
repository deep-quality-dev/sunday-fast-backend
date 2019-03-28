package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 房量
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:34
 */
@Data
@TableName("t_hotel_room_num")
public class HotelRoomNumEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 房型ID
	 */
	private Integer rid;
	/**
	 * 数量
	 */
	private Integer nums;
	/**
	 * 日期
	 */
	private Integer dateday;

}
