package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelOrderSettingDateEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 19:16:26
 */
public interface HotelOrderSettingDateService extends IService<HotelOrderSettingDateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

