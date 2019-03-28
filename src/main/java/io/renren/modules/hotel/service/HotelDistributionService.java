package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelDistributionEntity;

/**
 * 分销申请
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
public interface HotelDistributionService extends IService<HotelDistributionEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
