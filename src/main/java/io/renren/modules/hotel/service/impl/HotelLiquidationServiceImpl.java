package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelLiquidationDao;
import io.renren.modules.hotel.entity.HotelLiquidationEntity;
import io.renren.modules.hotel.service.HotelLiquidationService;


@Service("hotelLiquidationService")
public class HotelLiquidationServiceImpl extends ServiceImpl<HotelLiquidationDao, HotelLiquidationEntity> implements HotelLiquidationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelLiquidationEntity> page = this.page(
                new Query<HotelLiquidationEntity>().getPage(params),
                new QueryWrapper<HotelLiquidationEntity>()
        );

        return new PageUtils(page);
    }

}