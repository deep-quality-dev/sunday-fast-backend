package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelConsumptionRecordDao;
import io.renren.modules.hotel.entity.HotelConsumptionRecordEntity;
import io.renren.modules.hotel.service.HotelConsumptionRecordService;


@Service("hotelConsumptionRecordService")
public class HotelConsumptionRecordServiceImpl extends ServiceImpl<HotelConsumptionRecordDao, HotelConsumptionRecordEntity> implements HotelConsumptionRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelConsumptionRecordEntity> page = this.page(
                new Query<HotelConsumptionRecordEntity>().getPage(params),
                new QueryWrapper<HotelConsumptionRecordEntity>()
        );

        return new PageUtils(page);
    }

}