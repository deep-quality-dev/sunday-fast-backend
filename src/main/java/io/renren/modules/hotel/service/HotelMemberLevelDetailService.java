package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;

import java.util.Map;

/**
 * 会员卡详情
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-17 19:46:29
 */
public interface HotelMemberLevelDetailService extends IService<HotelMemberLevelDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

