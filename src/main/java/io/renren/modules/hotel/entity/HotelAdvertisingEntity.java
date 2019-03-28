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
 * @date 2019-03-20 12:49:40
 */
@Data
@TableName("t_hotel_advertising")
public class HotelAdvertisingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 轮播图标题
	 */
	private String title;
	/**
	 * 图片
	 */
	private String logo;
	/**
	 * 1.开启 2.关闭
	 */
	private Integer status;
	/**
	 * 链接
	 */
	private String src;
	/**
	 * 排序
	 */
	private Integer orderby;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 1开屏
	 */
	private int type;
	/**
	 * 外部链接
	 */
	private String wbSrc;
	/**
	 * 1内部，2外部,3跳转
	 */
	private Integer state;

}
