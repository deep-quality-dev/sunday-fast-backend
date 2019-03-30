package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelWxTemplateEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-31 00:25:08
 */
public interface HotelWxTemplateService extends IService<HotelWxTemplateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

