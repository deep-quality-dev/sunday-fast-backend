package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelOrderSettingEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 19:16:27
 */
public interface HotelOrderSettingService extends IService<HotelOrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存接单设置
     * @param hotelOrderSetting
     */
	void saveOrderSetting(HotelOrderSettingEntity hotelOrderSetting);

	/**
	 * 修改接单设置
	 * @param hotelOrderSetting
	 */
	void updateOrderSetting(HotelOrderSettingEntity hotelOrderSetting);
}

