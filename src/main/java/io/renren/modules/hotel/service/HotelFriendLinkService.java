package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelFriendLinkEntity;

import java.util.Map;

/**
 * 友情链接
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 15:41:25
 */
public interface HotelFriendLinkService extends IService<HotelFriendLinkEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

