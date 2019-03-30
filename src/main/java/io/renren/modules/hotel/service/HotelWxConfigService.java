package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-30 17:11:05
 */
public interface HotelWxConfigService extends IService<HotelWxConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

