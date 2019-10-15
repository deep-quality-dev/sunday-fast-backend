package io.renren.modules.hotel.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 商家会员
 * 
 * @author taoz
 *
 */
@Data
public class HotelSellerMemberDto {

	private Long userId;

	private Long id;

	private String img;

	private String nick;

	private String tel;

	private String name;

	private String cardNo;

	private String levelName;
	
	private String certificateNo;
	
	private int gender;

	private String level;

	private BigDecimal score;

	private BigDecimal balance;

	private String salesman;

	private Date joinTime;

	private Date createDate;

}
