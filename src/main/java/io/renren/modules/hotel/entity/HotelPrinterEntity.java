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
 * @date 2019-03-20 12:49:35
 */
@Data
@TableName("t_hotel_printer")
public class HotelPrinterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 打印机标题
	 */
	private String dyjTitle;
	/**
	 * 打印机编号
	 */
	private String dyjId;
	/**
	 * 打印机key
	 */
	private String dyjKey;
	/**
	 * 
	 */
	private String uniacid;
	/**
	 * 1.365 2.易联云，3飞蛾
	 */
	private Integer type;
	/**
	 * 打印机名称
	 */
	private String name;
	/**
	 * 打印机终端号
	 */
	private String mid;
	/**
	 * API密钥
	 */
	private String api;
	/**
	 * 商家ID
	 */
	private Integer sellerId;
	/**
	 * 1开启2关闭
	 */
	private Integer state;
	/**
	 * 用户id
	 */
	private String yyId;
	/**
	 * 打印机终端密钥
	 */
	private String token;
	/**
	 * 
	 */
	private String dyjTitle2;
	/**
	 * 
	 */
	private String dyjId2;
	/**
	 * 
	 */
	private String dyjKey2;
	/**
	 * 飞蛾账号
	 */
	private String fezh;
	/**
	 * ukey
	 */
	private String feUkey;
	/**
	 * 打印机编号
	 */
	private String feDycode;

}
