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
 * @date 2019-03-20 12:49:36
 */
@Data
@TableName("t_hotel_distribution_set")
public class HotelDistributionSetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 分销商申请协议
	 */
	private String distributionDetails;
	/**
	 * 佣金提现协议
	 */
	private String withdrawDetails;
	/**
	 * 1.开启分销审核2.不开启
	 */
	private Integer isDistribution;
	/**
	 * 是否开启二级分销1.是2.否
	 */
	private Integer isSecond;
	/**
	 * 提现手续费
	 */
	private Integer withdrawRate;
	/**
	 * 一级佣金
	 */
	private String commission;
	/**
	 * 二级佣金
	 */
	private String commission2;
	/**
	 * 提现门槛
	 */
	private Integer withdrawMoney;
	/**
	 * 分销中心图片
	 */
	private String img;
	/**
	 * 申请分销图片
	 */
	private String img2;
	/**
	 * 
	 */
	private Integer sellerId;
	/**
	 * 1.开启2关闭
	 */
	private Integer isOpen;
	/**
	 * 分销商说明
	 */
	private String instructions;
	/**
	 * 
	 */
	private Integer enabled;

}
