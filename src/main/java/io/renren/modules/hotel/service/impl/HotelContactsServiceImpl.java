package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelContactsDao;
import io.renren.modules.hotel.entity.HotelContactsEntity;
import io.renren.modules.hotel.service.HotelContactsService;


@Service("hotelContactsService")
public class HotelContactsServiceImpl extends ServiceImpl<HotelContactsDao, HotelContactsEntity> implements HotelContactsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelContactsEntity> page = this.page(
                new Query<HotelContactsEntity>().getPage(params),
                new QueryWrapper<HotelContactsEntity>()
        );

        return new PageUtils(page);
    }

}