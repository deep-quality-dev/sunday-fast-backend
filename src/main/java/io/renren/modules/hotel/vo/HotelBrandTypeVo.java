package io.renren.modules.hotel.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HotelBrandTypeVo {
	private Long id;

	private String name;

	private List<HotelBrandVo> brands = new ArrayList<HotelBrandVo>();
}
