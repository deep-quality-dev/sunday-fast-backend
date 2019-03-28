package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@TableName("t_hotel_member")
public class HotelMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 注册时间
	 */
	private Date joinTime;
	/**
	 * 头像
	 */
	private String img;
	/**
	 * openID
	 */
	private String openid;
	/**
	 * 商家
	 */
	private Long sellerId;
	/**
	 * 手机号
	 */
	private String tel;
	/**
	 * 1不是会员,2是会员
	 */
	private Integer type;
	/**
	 * 会员等级id
	 */
	private Long levelId;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 真是姓名
	 */
	private String zsName;
	/**
	 * 会员卡号
	 */
	private String number;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 余额
	 */
	private BigDecimal balance;

}
