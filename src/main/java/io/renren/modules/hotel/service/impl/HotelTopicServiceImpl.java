package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelTopicDao;
import io.renren.modules.hotel.entity.HotelTopicEntity;
import io.renren.modules.hotel.service.HotelTopicService;


@Service("hotelTopicService")
public class HotelTopicServiceImpl extends ServiceImpl<HotelTopicDao, HotelTopicEntity> implements HotelTopicService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelTopicEntity> page = this.page(
                new Query<HotelTopicEntity>().getPage(params),
                new QueryWrapper<HotelTopicEntity>()
        );

        return new PageUtils(page);
    }

}