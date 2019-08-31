package io.renren.modules.hotel.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "酒店列表查询参数")
public class HotelSearchCondition {

	@ApiModelProperty(value = "地区")
	private String area;

	@ApiModelProperty(value = "入住日期")
	private String date;

	@ApiModelProperty(value = "房间类型，1-普通，0-钟点房")
	private int roomType;

	@ApiModelProperty(value = "用户定位信息")
	private String lonLat;
}
