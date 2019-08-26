package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelCouponsCashDao;
import io.renren.modules.hotel.entity.HotelCouponsCashEntity;
import io.renren.modules.hotel.service.HotelCouponsCashService;


@Service("hotelCouponsCashService")
public class HotelCouponsCashServiceImpl extends ServiceImpl<HotelCouponsCashDao, HotelCouponsCashEntity> implements HotelCouponsCashService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelCouponsCashEntity> page = this.page(
                new Query<HotelCouponsCashEntity>().getPage(params),
                new QueryWrapper<HotelCouponsCashEntity>()
        );

        return new PageUtils(page);
    }

}