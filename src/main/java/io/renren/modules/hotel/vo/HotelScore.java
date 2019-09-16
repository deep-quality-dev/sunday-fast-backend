package io.renren.modules.hotel.vo;

import java.util.Date;

import lombok.Data;

@Data
public class HotelScore {
	private Long id;
	
	private String score;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 时间
	 */
	private Date createTime;
}
