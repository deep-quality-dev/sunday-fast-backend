package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelLiquidationRecordEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:18
 */
public interface HotelLiquidationRecordService extends IService<HotelLiquidationRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

