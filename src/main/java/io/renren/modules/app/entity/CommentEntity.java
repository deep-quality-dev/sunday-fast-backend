package io.renren.modules.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 对应order_goods中的ID
	 */
	private Integer ogid;
	/**
	 *
	 */
	private Integer gid;
	/**
	 *
	 */
	private Integer uid;
	/**
	 *
	 */
	private String content;
	/**
	 *
	 */
	private Date addtime;

}
