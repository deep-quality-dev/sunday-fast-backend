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
@TableName("t_ad")
public class AdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 幻灯片0 广告1
	 */
	private Integer type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 链接的商品ID
	 */
	private Integer gid;
	/**
	 * 排序
	 */
	private Integer sort;

}
