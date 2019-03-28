package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelIntegralClassifyEntity;

/**
 * 积分商城分类
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
public interface HotelIntegralClassifyService extends IService<HotelIntegralClassifyEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
