package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-21 22:44:37
 */
public interface HotelRoomPriceService extends IService<HotelRoomPriceEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
