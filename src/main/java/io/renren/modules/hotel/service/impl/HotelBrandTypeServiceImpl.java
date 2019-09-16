package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelBrandTypeDao;
import io.renren.modules.hotel.entity.HotelBrandTypeEntity;
import io.renren.modules.hotel.service.HotelBrandTypeService;


@Service("hotelBrandTypeService")
public class HotelBrandTypeServiceImpl extends ServiceImpl<HotelBrandTypeDao, HotelBrandTypeEntity> implements HotelBrandTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelBrandTypeEntity> page = this.page(
                new Query<HotelBrandTypeEntity>().getPage(params),
                new QueryWrapper<HotelBrandTypeEntity>()
        );

        return new PageUtils(page);
    }

}