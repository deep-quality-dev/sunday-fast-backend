package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelLiquidationSettingDao;
import io.renren.modules.hotel.entity.HotelLiquidationSettingEntity;
import io.renren.modules.hotel.service.HotelLiquidationSettingService;


@Service("hotelLiquidationSettingService")
public class HotelLiquidationSettingServiceImpl extends ServiceImpl<HotelLiquidationSettingDao, HotelLiquidationSettingEntity> implements HotelLiquidationSettingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelLiquidationSettingEntity> page = this.page(
                new Query<HotelLiquidationSettingEntity>().getPage(params),
                new QueryWrapper<HotelLiquidationSettingEntity>()
        );

        return new PageUtils(page);
    }

}