package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelHelepMenuEntity;

import java.util.Map;

/**
 *  帮助文档

 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 15:41:25
 */
public interface HotelHelepMenuService extends IService<HotelHelepMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

