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
@TableName("t_hotel_integral_record")
public class HotelIntegralRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 商品id
	 */
	private Integer goodId;
	/**
	 * 兑换时间
	 */
	private String time;
	/**
	 * 用户地址
	 */
	private String userName;
	/**
	 * 用户电话
	 */
	private String userTel;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 
	 */
	private String note;
	/**
	 * 积分
	 */
	private Integer integral;
	/**
	 * 商品名称
	 */
	private String goodName;
	/**
	 * 
	 */
	private String goodImg;
	/**
	 * 1.未处理 2.已处理
	 */
	private Integer state;
	/**
	 * 快递公司
	 */
	private String kdName;
	/**
	 * 快递编号
	 */
	private String kdNum;

}
