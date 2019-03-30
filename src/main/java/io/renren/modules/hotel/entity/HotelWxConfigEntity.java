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
 * @email 18819175397@163.com
 * @date 2019-03-30 17:25:36
 */
@Data
@TableName("t_hotel_wx_config")
public class HotelWxConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String appId;
	/**
	 * 
	 */
	private String secret;
	/**
	 * 
	 */
	private String mchId;
	/**
	 * 
	 */
	private String mchKey;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private Integer enabled;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String aesKey;
	/**
	 * 
	 */
	private String token;

}
