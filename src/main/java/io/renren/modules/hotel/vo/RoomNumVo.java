package io.renren.modules.hotel.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 房量管理数据
 * 
 * @author taoz
 *
 */
@Data
public class RoomNumVo {
	/**
	 * 日期数据，默认一个月
	 */
	private List<String> date = new ArrayList<String>();

	/**
	 * 价格数据
	 */
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
}
