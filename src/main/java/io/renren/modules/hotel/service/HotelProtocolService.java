package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelProtocolEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-18 20:38:22
 */
public interface HotelProtocolService extends IService<HotelProtocolEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

