package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelNoticeEntity;

/**
 * 通知表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
public interface HotelNoticeService extends IService<HotelNoticeEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
