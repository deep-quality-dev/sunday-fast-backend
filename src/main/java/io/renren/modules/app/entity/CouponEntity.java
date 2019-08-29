package io.renren.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 优惠券记录表
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 优惠券名称
	 */
	private String name;
	/**
	 * 优惠券类型(0满减券 1折扣券)
	 */
	private Integer type;
	/**
	 * 满减券-减免金额
	 */
	private BigDecimal price;
	/**
	 * 折扣券-折扣率(1-100)
	 */
	private Integer discount;
	/**
	 * 最低消费金额
	 */
	private BigDecimal minPrice;
	/**
	 * 优惠券开始时间
	 */
	private Integer startTime;
	/**
	 * 优惠券结束时间
	 */
	private Integer endTime;
	/**
	 * 已领取数量
	 */
	private Integer receiveNum;
	/**
	 * 软删除
	 */
	private Integer isDelete;
	/**
	 * 排序
	 */
	private Integer sort;

}
