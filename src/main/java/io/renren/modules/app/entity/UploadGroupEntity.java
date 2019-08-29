package io.renren.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-06-05 15:33:58
 */
@Data
@TableName("t_upload_group")
public class UploadGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	private Integer id;
	/**
	 * 文件类型
	 */
	private String groupType;
	/**
	 * 分类名称
	 */
	private String groupName;
	/**
	 * 分类排序(数字越小越靠前)
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Integer createTime;
	/**
	 * 更新时间
	 */
	private Integer updateTime;

}
