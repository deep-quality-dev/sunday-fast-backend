package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelWxConfigDao;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.service.HotelWxConfigService;


@Service("hotelWxConfigService")
public class HotelWxConfigServiceImpl extends ServiceImpl<HotelWxConfigDao, HotelWxConfigEntity> implements HotelWxConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelWxConfigEntity> page = this.page(
                new Query<HotelWxConfigEntity>().getPage(params),
                new QueryWrapper<HotelWxConfigEntity>()
        );

        return new PageUtils(page);
    }

}