package io.renren.modules.hotel.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelAdvertisingEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:40
 */
public interface HotelAdvertisingService extends IService<HotelAdvertisingEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据类型加载广告
	 * 
	 * @param sellerId
	 * @param type
	 * @return
	 */
	List<HotelAdvertisingEntity> loadByType(int type);
}
