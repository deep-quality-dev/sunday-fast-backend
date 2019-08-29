package io.renren.modules.app.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Data
@TableName("t_spec")
public class SpecEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 分类ID
	 */
	private Integer cid;
	/**
	 * 规格名
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;

}
