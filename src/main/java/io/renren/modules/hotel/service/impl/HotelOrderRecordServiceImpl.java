package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelOrderRecordDao;
import io.renren.modules.hotel.entity.HotelOrderRecordEntity;
import io.renren.modules.hotel.service.HotelOrderRecordService;


@Service("hotelOrderRecordService")
public class HotelOrderRecordServiceImpl extends ServiceImpl<HotelOrderRecordDao, HotelOrderRecordEntity> implements HotelOrderRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelOrderRecordEntity> page = this.page(
                new Query<HotelOrderRecordEntity>().getPage(params),
                new QueryWrapper<HotelOrderRecordEntity>()
        );

        return new PageUtils(page);
    }

}