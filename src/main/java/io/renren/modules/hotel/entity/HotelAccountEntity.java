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
 * @date 2019-03-20 12:49:39
 */
@Data
@TableName("t_hotel_account")
public class HotelAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 所属帐号
	 */
	private Integer weid;
	/**
	 * 门店id
	 */
	private String storeid;
	/**
	 * 
	 */
	private Integer uid;
	/**
	 * 
	 */
	private String fromUser;
	/**
	 * 
	 */
	private String accountname;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private String salt;
	/**
	 * 
	 */
	private String pwd;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String payAccount;
	/**
	 * 排序
	 */
	private Integer displayorder;
	/**
	 * 
	 */
	private Integer dateline;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 1:店长,2:店员
	 */
	private Integer role;
	/**
	 * 
	 */
	private Integer lastvisit;
	/**
	 * 
	 */
	private String lastip;
	/**
	 * 区域id
	 */
	private Integer areaid;
	/**
	 * 
	 */
	private Integer isAdminOrder;
	/**
	 * 
	 */
	private Integer isNoticeOrder;
	/**
	 * 
	 */
	private Integer isNoticeQueue;
	/**
	 * 
	 */
	private Integer isNoticeService;
	/**
	 * 
	 */
	private Integer isNoticeBoss;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 经度
	 */
	private BigDecimal lat;
	/**
	 * 纬度
	 */
	private BigDecimal lng;
	/**
	 * 
	 */
	private String authority;

}
