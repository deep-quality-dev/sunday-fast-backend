package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 22:32:03
 */
@Data
@TableName("t_hotel_seller_employee")
public class HotelSellerEmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long sellerId;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 姓名
	 */
	@NotBlank(message = "姓名不能为空")
	private String name;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String userName;
	/**
	 * 手机
	 */
	@NotBlank(message = "手机不能为空")
	private String mobile;
	/**
	 * 
	 */
	private String password;
	/**
	 * 邮箱
	 */
	@NotBlank(message = "邮箱不能为空")
	private String email;

}
