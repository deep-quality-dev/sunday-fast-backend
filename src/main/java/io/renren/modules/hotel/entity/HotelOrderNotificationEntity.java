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
 * @date 2019-10-26 17:47:02
 */
@Data
@TableName("t_hotel_order_notification")
public class HotelOrderNotificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 短信开关
	 */
	private Integer smsSwitch;
	/**
	 * 邮件开关
	 */
	private Integer emailSwitch;
	/**
	 * pc弹窗开关
	 */
	private Integer pcSwitch;
	/**
	 * 
	 */
	private Long sellerId;

}
