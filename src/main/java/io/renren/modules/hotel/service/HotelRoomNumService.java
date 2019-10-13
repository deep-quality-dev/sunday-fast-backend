package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.hutool.db.Page;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
import io.renren.modules.hotel.vo.RoomNumVo;

/**
 * 房量
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:34
 */
public interface HotelRoomNumService extends IService<HotelRoomNumEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 房量数据
	 * 
	 * @param sellerId
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @return
	 */
	RoomNumVo roomNumData(Long sellerId, String startDate, String endDate, Page page);

	/**
	 * 更新制定日期的房量
	 * @param params
	 */
	void update4Day(Map<String, Object> params);
}
