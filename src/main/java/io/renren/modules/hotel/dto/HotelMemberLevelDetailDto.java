package io.renren.modules.hotel.dto;

import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import lombok.Data;

@Data
public class HotelMemberLevelDetailDto extends HotelMemberLevelDetailEntity{
	
	private String levelName;
	
}
