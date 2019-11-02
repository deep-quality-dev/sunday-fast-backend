package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelCouponsRoomsDao;
import io.renren.modules.hotel.entity.HotelCouponsRoomsEntity;
import io.renren.modules.hotel.service.HotelCouponsRoomsService;


@Service("hotelCouponsRoomsService")
public class HotelCouponsRoomsServiceImpl extends ServiceImpl<HotelCouponsRoomsDao, HotelCouponsRoomsEntity> implements HotelCouponsRoomsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelCouponsRoomsEntity> page = this.page(
                new Query<HotelCouponsRoomsEntity>().getPage(params),
                new QueryWrapper<HotelCouponsRoomsEntity>()
        );

        return new PageUtils(page);
    }

}