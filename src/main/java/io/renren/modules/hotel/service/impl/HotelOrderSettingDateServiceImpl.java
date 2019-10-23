package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelOrderSettingDateDao;
import io.renren.modules.hotel.entity.HotelOrderSettingDateEntity;
import io.renren.modules.hotel.service.HotelOrderSettingDateService;


@Service("hotelOrderSettingDateService")
public class HotelOrderSettingDateServiceImpl extends ServiceImpl<HotelOrderSettingDateDao, HotelOrderSettingDateEntity> implements HotelOrderSettingDateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelOrderSettingDateEntity> page = this.page(
                new Query<HotelOrderSettingDateEntity>().getPage(params),
                new QueryWrapper<HotelOrderSettingDateEntity>()
        );

        return new PageUtils(page);
    }

}