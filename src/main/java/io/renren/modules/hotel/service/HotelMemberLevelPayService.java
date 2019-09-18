package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelMemberLevelPayEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-18 21:58:26
 */
public interface HotelMemberLevelPayService extends IService<HotelMemberLevelPayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

