package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelAssessEntity;

/**
 * 评价表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
public interface HotelAssessService extends IService<HotelAssessEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
