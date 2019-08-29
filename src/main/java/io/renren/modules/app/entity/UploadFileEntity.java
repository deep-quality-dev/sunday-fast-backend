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
@TableName("t_upload_file")
public class UploadFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件id
	 */
	@TableId
	private Integer id;
	/**
	 * 存储方式
	 */
	private String storage;
	/**
	 * 文件分组id
	 */
	private Integer groupId;
	/**
	 * 存储域名
	 */
	private String fileUrl;
	/**
	 * 文件路径
	 */
	private String fileName;
	/**
	 * 文件大小(字节)
	 */
	private Integer fileSize;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件扩展名
	 */
	private String extension;
	/**
	 * 软删除
	 */
	private Integer isDelete;
	/**
	 * 创建时间
	 */
	private Integer createTime;

}
