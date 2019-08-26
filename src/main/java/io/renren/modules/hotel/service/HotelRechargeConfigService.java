package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRechargeConfigEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-26 22:10:48
 */
public interface HotelRechargeConfigService extends IService<HotelRechargeConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

