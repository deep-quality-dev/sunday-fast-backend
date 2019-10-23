package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelOrderSettingDao;
import io.renren.modules.hotel.entity.HotelOrderSettingEntity;
import io.renren.modules.hotel.service.HotelOrderSettingService;


@Service("hotelOrderSettingService")
public class HotelOrderSettingServiceImpl extends ServiceImpl<HotelOrderSettingDao, HotelOrderSettingEntity> implements HotelOrderSettingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelOrderSettingEntity> page = this.page(
                new Query<HotelOrderSettingEntity>().getPage(params),
                new QueryWrapper<HotelOrderSettingEntity>()
        );

        return new PageUtils(page);
    }

}