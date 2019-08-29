package io.renren.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 父ID
	 */
	private Integer pid;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像 从小程序获取
	 */
	private String head;
	/**
	 * 用户金额
	 */
	private BigDecimal money;
	/**
	 * 消费累计
	 */
	private BigDecimal totalAmount;
	/**
	 * 注册时间
	 */
	private Date regTime;
	/**
	 * 微信验证后返回openid
	 */
	private String openid;
	/**
	 *
	 */
	private String token;
	/**
	 *
	 */
	private Integer tokenTime;
	/**
	 * 冻结佣金
	 */
	private BigDecimal moneyFrozen;
	/**
	 * 可提现佣金
	 */
	private BigDecimal moneyCash;
	/**
	 * 已提现佣金
	 */
	private BigDecimal moneyCashed;
	/**
	 * 一级会员
	 */
	private Integer firstMember;
	/**
	 * 二级会员
	 */
	private Integer secondMember;
	/**
	 * 三级会员
	 */
	private Integer thirdMember;

}
