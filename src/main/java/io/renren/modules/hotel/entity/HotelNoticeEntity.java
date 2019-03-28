package io.renren.modules.hotel.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 通知表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@Data
@TableName("t_hotel_notice")
public class HotelNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 商家ID
	 */
	private Integer sellerId;
	/**
	 * 接收人手机号
	 */
	private String jsTel;
	/**
	 * 模板id
	 */
	private String tplId;
	/**
	 * 应用密钥
	 */
	private String appkey;
	/**
	 * 
	 */
	private Integer uniacid;

}
