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
 * @date 2019-03-20 12:49:34
 */
@Data
@TableName("t_hotel_nav")
public class HotelNavEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String title;
	/**
	 * 图标
	 */
	private String logo;
	/**
	 * 1.开启 2.关闭
	 */
	private Integer status;
	/**
	 * 内部链接
	 */
	private String src;
	/**
	 * 排序
	 */
	private Integer orderby;
	/**
	 * 小程序名称
	 */
	private String xcxName;
	/**
	 * APPID
	 */
	private String appid;
	/**
	 * 小程序id
	 */
	private Integer uniacid;
	/**
	 * 外部链接
	 */
	private String wbSrc;
	/**
	 * 1内部，2外部,3跳转
	 */
	private Integer state;

}
