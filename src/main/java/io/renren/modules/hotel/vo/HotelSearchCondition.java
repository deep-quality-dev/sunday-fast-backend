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

	@ApiModelProperty(value = "定位信息 /纬度经度")
	private String lonLat;

	@ApiModelProperty(value = "范围，公里数")
	private double dis;

	@ApiModelProperty(value = "排序，1-距离，2-价格，3-评价")
	private int sort;

	@ApiModelProperty(value = "酒店星级 1-经济型，2-二星级，3-三星级，4-4星级，5-星级")
	private int hotelStar;

	@ApiModelProperty(value = "酒店服务 1-健身房，2-会议室，")
	private int hotelService;

	@ApiModelProperty(value = "关键字")
	private String k;

	@ApiModelProperty(value = "主题类型，这里待定，数据可能需要后台配置")
	private String topic;

	@ApiModelProperty(value = "入住时间")
	private String startDay;

	@ApiModelProperty(value = "离店时间")
	private String endDay;

	private double latitude;
	private double longitude;
}
