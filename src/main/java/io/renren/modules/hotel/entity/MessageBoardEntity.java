package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 留言板
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-22 21:41:35
 */
@Data
@TableName("t_message_board")
public class MessageBoardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 联系人
	 */
	private String name;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 时间
	 */
	private Date createTime;

}
