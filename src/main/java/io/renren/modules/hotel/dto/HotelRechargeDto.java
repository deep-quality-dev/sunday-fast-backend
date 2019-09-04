package io.renren.modules.hotel.dto;

import io.renren.modules.hotel.entity.HotelRechargeEntity;
import lombok.Data;

@Data
public class HotelRechargeDto extends HotelRechargeEntity {

	private String sellerName;

	private String memberName;
}
