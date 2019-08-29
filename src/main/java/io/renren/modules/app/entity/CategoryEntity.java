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
@TableName("t_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品分类id
	 */
	@TableId
	private Integer id;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 父ID
	 */
	private Integer pid;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 首页显示
	 */
	private Integer isShowIndex;
	/**
	 * 排序
	 */
	private Integer sort;

}
