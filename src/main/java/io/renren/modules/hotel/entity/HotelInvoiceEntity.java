package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 发票
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
@Data
@TableName("t_hotel_invoice")
public class HotelInvoiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 纳税人识别号
	 */
	private String identifyNumber;
	/**
	 * 开户行
	 */
	private String openingBank;
	/**
	 * 银行名称
	 */
	private String bank;
	/**
	 * 银行地址
	 */
	private String bankAddress;
	/**
	 * 注册地址
	 */
	private String registAddress;
	/**
	 * 公司电话
	 */
	private String companyPhone;
	/**
	 * 发票类型
	 */
	private Integer type;
	/**
	 * 状态
	 */
	private Integer enabled;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
