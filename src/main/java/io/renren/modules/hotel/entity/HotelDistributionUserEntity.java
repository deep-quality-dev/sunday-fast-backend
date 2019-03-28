package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@Data
@TableName("t_hotel_distribution_user")
public class HotelDistributionUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 一级分销
	 */
	private Integer userId;
	/**
	 * 二级分销
	 */
	private Integer fxUser;
	/**
	 * 
	 */
	private Integer time;

}
