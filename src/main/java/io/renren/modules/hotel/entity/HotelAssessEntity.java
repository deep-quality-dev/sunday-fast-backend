package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 评价表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
@Data
@TableName("t_hotel_assess")
public class HotelAssessEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 创建时间
	 */
	private Integer time;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 商家回复
	 */
	private String reply;
	/**
	 * 评价状态1，未回复，2已回复
	 */
	private Integer status;
	/**
	 * 回复时间
	 */
	private Integer replyTime;

	/**
	 * 评论类型 1-酒店 2-商品
	 */
	private Integer type;

	/**
	 * 订单ID
	 */
	private Long orderId;

	/**
	 * 商品ID
	 */
	private Long goodsId;

}
