package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelOrderRecordEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-23 22:40:39
 */
public interface HotelOrderRecordService extends IService<HotelOrderRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

