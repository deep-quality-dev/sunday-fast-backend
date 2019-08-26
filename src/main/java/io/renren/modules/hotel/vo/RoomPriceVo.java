package io.renren.modules.hotel.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RoomPriceVo {

	/**
	 * 日期数据，默认一个月
	 */
	private List<String> date = new ArrayList<String>();

	/**
	 * 价格数据
	 */
	private List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
}
