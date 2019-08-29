package io.renren.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_address")
public class AddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer uid;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 *
	 */
	private String region;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 默认收货地址
	 */
	private Integer isDefault;

	private Integer provinceId;
	private Integer cityId;
	private Integer areaId;

}
