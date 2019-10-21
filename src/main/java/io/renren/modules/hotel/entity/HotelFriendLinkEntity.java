package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 友情链接
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 15:41:25
 */
@Data
@TableName("t_hotel_friend_link")
public class HotelFriendLinkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 链接地址
	 */
	private String url;
	/**
	 * 创建时间
	 */
	private Date createtime;

}
