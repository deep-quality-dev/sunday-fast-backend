package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelLiquidationRecordDao;
import io.renren.modules.hotel.entity.HotelLiquidationRecordEntity;
import io.renren.modules.hotel.service.HotelLiquidationRecordService;


@Service("hotelLiquidationRecordService")
public class HotelLiquidationRecordServiceImpl extends ServiceImpl<HotelLiquidationRecordDao, HotelLiquidationRecordEntity> implements HotelLiquidationRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelLiquidationRecordEntity> page = this.page(
                new Query<HotelLiquidationRecordEntity>().getPage(params),
                new QueryWrapper<HotelLiquidationRecordEntity>()
        );

        return new PageUtils(page);
    }

}