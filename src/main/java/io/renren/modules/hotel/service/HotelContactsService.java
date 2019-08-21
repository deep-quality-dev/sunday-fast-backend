package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelContactsEntity;

import java.util.Map;

/**
 * 联系人
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
public interface HotelContactsService extends IService<HotelContactsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

