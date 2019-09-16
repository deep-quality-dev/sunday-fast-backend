package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 积分明细表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:32
 */
@Data
@TableName("t_hotel_score")
public class HotelScoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;

	private Long sellerId;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 评论id
	 */
	private Long assessId;
	/**
	 * 积分
	 */
	private String score;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 时间
	 */
	private Date createTime;

	/**
	 * 业务时间
	 */
	private Long time;
	/**
	 * 
	 */
	private Integer goodsId;
	/**
	 * 类型 1 签到，2 订房
	 */
	private Integer type;
	
	/**
	 * 会员卡ID
	 */
	private Long cardId;

}
