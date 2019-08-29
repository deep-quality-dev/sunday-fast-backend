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
@TableName("t_goods_images")
public class GoodsImagesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品图片id
	 */
	@TableId
	private Integer id;
	/**
	 * 商品id
	 */
	private Integer gid;
	/**
	 * 图片地址
	 */
	private String img;

}
