package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 会员等级表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@Data
@TableName("t_hotel_member_level")
public class HotelMemberLevelEntity implements Serializable {
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
	 * 会员名称
	 */
	private String name;

	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 折扣
	 */
	private String discount;
	/**
	 * 排序
	 */
	private Integer orderby;

	/**
	 * 描述
	 */
	private String content;

	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;

	/**
	 * 是否需要支付
	 */
	private int payFlag = 0;

	/**
	 * 卡片背景图
	 */
	private String bgImage;

}
