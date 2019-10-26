package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelOrderNotificationEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-26 17:47:02
 */
public interface HotelOrderNotificationService extends IService<HotelOrderNotificationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

