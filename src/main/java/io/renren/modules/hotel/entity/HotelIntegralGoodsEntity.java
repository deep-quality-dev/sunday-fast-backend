package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@Data
@TableName("t_hotel_integral_goods")
public class HotelIntegralGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 主图
	 */
	private String img;
	/**
	 * 所需积分
	 */
	private Integer score;
	/**
	 * 分类id
	 */
	private Integer typeId;
	/**
	 * 商品详情
	 */
	private String goodsDetails;
	/**
	 * 
	 */
	private String processDetails;
	/**
	 * 
	 */
	private String attentionDetails;
	/**
	 * 数量
	 */
	private Integer number;
	/**
	 * 期限
	 */
	private String time;
	/**
	 * 1.开启2关闭
	 */
	private Integer isOpen;
	/**
	 * 1.余额2.实物
	 */
	private Integer type;
	/**
	 * 排序
	 */
	private Integer num;
	/**
	 * 兑换截止时间
	 */
	private Integer endTime;
	/**
	 * 商家ID
	 */
	private Integer sellerId;
	/**
	 * 
	 */
	private BigDecimal hbMoeny;
	/**
	 * 
	 */
	private Integer enabled;

}
