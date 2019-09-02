package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.hutool.db.Page;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.vo.RoomPriceVo;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-21 22:44:37
 */
public interface HotelRoomPriceService extends IService<HotelRoomPriceEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 房价维护
	 * @param sellerId 
	 * 
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @return
	 */
	RoomPriceVo roomPrice(Long sellerId, String startDate, String endDate, Page page);
}
