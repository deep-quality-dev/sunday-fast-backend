package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelFriendLinkDao;
import io.renren.modules.hotel.entity.HotelFriendLinkEntity;
import io.renren.modules.hotel.service.HotelFriendLinkService;


@Service("hotelFriendLinkService")
public class HotelFriendLinkServiceImpl extends ServiceImpl<HotelFriendLinkDao, HotelFriendLinkEntity> implements HotelFriendLinkService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelFriendLinkEntity> page = this.page(
                new Query<HotelFriendLinkEntity>().getPage(params),
                new QueryWrapper<HotelFriendLinkEntity>()
        );

        return new PageUtils(page);
    }

}