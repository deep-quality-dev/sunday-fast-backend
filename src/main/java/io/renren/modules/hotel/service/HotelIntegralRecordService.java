package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelIntegralRecordEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
public interface HotelIntegralRecordService extends IService<HotelIntegralRecordEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
