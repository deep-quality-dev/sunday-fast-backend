package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 联系人
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
@Data
@TableName("t_hotel_contacts")
public class HotelContactsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Integer memberId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 状态
	 */
	private Integer enabled;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
