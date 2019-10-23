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
 * @date 2019-10-23 19:16:26
 */
@Data
@TableName("t_hotel_order_setting_date")
public class HotelOrderSettingDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private String date;
	/**
	 * 
	 */
	private String time;
	/**
	 * 
	 */
	private Long settingId;

}
