package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 积分商城分类
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
@Data
@TableName("t_hotel_integral_classify")
public class HotelIntegralClassifyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String img;
	/**
	 * 
	 */
	private Integer num;
	/**
	 * 
	 */
	private Integer uniacid;

}
