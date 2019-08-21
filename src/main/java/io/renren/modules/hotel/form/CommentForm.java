package io.renren.modules.hotel.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评价表单
 * 
 * @author taoz
 *
 */
@Data
@ApiModel(value = "评论表单")
public class CommentForm {
	/**
	 * 分数
	 */
	@ApiModelProperty(value = "分数")
	@NotBlank(message = "分数不能为空")
	private Integer score;
	/**
	 * 评价内容
	 */
	@ApiModelProperty(value = "评价内容")
	@NotBlank(message = "评价内容不能为空")
	private String content;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	private String img;

	@ApiModelProperty(value = "类型 1-酒店，2-上")
	@NotBlank(message = "评价类型不能为空")
	private int type;
	
	@ApiModelProperty(value = "对应酒店订单或者商品订单")
	@NotBlank(message = "业务ID不能为空")
	private Long bizId;
}
