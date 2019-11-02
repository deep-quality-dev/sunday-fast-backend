package io.renren.modules.hotel.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	@TableField(exist = false)
	private String memberName;

	@TableField(exist = false)
	private String memberImg;

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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Long time;
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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Long replyTime;

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
