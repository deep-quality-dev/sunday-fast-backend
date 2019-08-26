package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelRechargeConfigDao;
import io.renren.modules.hotel.entity.HotelRechargeConfigEntity;
import io.renren.modules.hotel.service.HotelRechargeConfigService;


@Service("hotelRechargeConfigService")
public class HotelRechargeConfigServiceImpl extends ServiceImpl<HotelRechargeConfigDao, HotelRechargeConfigEntity> implements HotelRechargeConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelRechargeConfigEntity> page = this.page(
                new Query<HotelRechargeConfigEntity>().getPage(params),
                new QueryWrapper<HotelRechargeConfigEntity>()
        );

        return new PageUtils(page);
    }

}