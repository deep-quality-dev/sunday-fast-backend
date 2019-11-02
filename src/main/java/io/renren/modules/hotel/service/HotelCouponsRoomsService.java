package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelCouponsRoomsEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-11-02 10:17:07
 */
public interface HotelCouponsRoomsService extends IService<HotelCouponsRoomsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

