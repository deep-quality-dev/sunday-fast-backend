package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelHelepMenuDao;
import io.renren.modules.hotel.entity.HotelHelepMenuEntity;
import io.renren.modules.hotel.service.HotelHelepMenuService;


@Service("hotelHelepMenuService")
public class HotelHelepMenuServiceImpl extends ServiceImpl<HotelHelepMenuDao, HotelHelepMenuEntity> implements HotelHelepMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelHelepMenuEntity> page = this.page(
                new Query<HotelHelepMenuEntity>().getPage(params),
                new QueryWrapper<HotelHelepMenuEntity>()
        );

        return new PageUtils(page);
    }

}