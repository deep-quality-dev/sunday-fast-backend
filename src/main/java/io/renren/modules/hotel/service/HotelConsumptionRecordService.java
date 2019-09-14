package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelConsumptionRecordEntity;

/**
 * 会员卡消费明细
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-28 22:24:24
 */
public interface HotelConsumptionRecordService extends IService<HotelConsumptionRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

