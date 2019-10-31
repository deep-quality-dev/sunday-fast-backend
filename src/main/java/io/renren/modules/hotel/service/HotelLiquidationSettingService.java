package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelLiquidationSettingEntity;

import java.util.Map;

/**
 * 结算设置
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:19
 */
public interface HotelLiquidationSettingService extends IService<HotelLiquidationSettingEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 添加结算设置
	 * 
	 * @param hotelLiquidationSettingEntity
	 */
	void saveLiquidationSetting(HotelLiquidationSettingEntity hotelLiquidationSettingEntity);

	/**
	 * 修改结算设置
	 * 
	 * @param hotelLiquidationSetting
	 */
	void updateLiquidationSetting(HotelLiquidationSettingEntity hotelLiquidationSetting);
}
