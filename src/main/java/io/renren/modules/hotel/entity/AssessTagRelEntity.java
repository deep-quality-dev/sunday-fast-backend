package io.renren.modules.hotel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-10 16:26:07
 */
@Data
@TableName("t_assess_tag_rel")
public class AssessTagRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 评论ID
	 */
	private Long assessId;
	/**
	 * tagID
	 */
	private Long tagId;
	
}
