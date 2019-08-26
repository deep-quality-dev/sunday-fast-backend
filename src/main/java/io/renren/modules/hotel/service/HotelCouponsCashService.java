package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelCouponsCashEntity;

import java.util.Map;

/**
 * 代金券
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-26 22:10:49
 */
public interface HotelCouponsCashService extends IService<HotelCouponsCashEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

