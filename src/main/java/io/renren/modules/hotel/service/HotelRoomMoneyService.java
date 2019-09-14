package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-21 22:44:37
 */
public interface HotelRoomMoneyService extends IService<HotelRoomMoneyEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 更新房量
	 * 
	 * @param hotelRoomMoneyEntity
	 * @param roomNum
	 */
	void updateRoomNum(HotelRoomMoneyEntity hotelRoomMoneyEntity, int roomNum);
}
