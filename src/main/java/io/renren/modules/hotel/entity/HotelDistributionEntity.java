package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 分销申请
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@Data
@TableName("t_hotel_distribution")
public class HotelDistributionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户
	 */
	private Integer userId;
	/**
	 * 时间
	 */
	private Integer time;
	/**
	 * 用户昵称
	 */
	private String userName;
	/**
	 * 电话
	 */
	private String userTel;
	/**
	 * 1.审核中2.通过3.拒绝
	 */
	private Integer state;
	/**
	 * 商家
	 */
	private Integer sellerId;

}
