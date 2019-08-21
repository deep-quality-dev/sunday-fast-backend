package io.renren.modules.hotel.vo;

import lombok.Data;

@Data
public class CommentItemVo {
	/**
	 * 
	 */
	private Long id;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 图片
	 */
	private String img;

	private String nick;

	private String headPhoto;
}
