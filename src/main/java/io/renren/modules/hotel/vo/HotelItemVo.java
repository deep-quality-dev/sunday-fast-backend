package io.renren.modules.hotel.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HotelItemVo {

	private Long id;

	private String logo;

	private String name;

	private List<String> labelds;

	private int commentCount;

	private String lonLat;

	private float km;

	private String price;
	
	private BigDecimal score;
}
