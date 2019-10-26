package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelOrderNotificationDao;
import io.renren.modules.hotel.entity.HotelOrderNotificationEntity;
import io.renren.modules.hotel.service.HotelOrderNotificationService;


@Service("hotelOrderNotificationService")
public class HotelOrderNotificationServiceImpl extends ServiceImpl<HotelOrderNotificationDao, HotelOrderNotificationEntity> implements HotelOrderNotificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelOrderNotificationEntity> page = this.page(
                new Query<HotelOrderNotificationEntity>().getPage(params),
                new QueryWrapper<HotelOrderNotificationEntity>()
        );

        return new PageUtils(page);
    }

}