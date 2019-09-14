package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-31 00:25:08
 */
@Data
@TableName("t_hotel_wx_template")
public class HotelWxTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String templateId;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer enabled;
	/**
	 * 
	 */
	private String name;

}
