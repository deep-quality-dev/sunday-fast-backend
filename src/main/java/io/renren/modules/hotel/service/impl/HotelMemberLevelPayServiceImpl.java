package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelMemberLevelPayDao;
import io.renren.modules.hotel.entity.HotelMemberLevelPayEntity;
import io.renren.modules.hotel.service.HotelMemberLevelPayService;


@Service("hotelMemberLevelPayService")
public class HotelMemberLevelPayServiceImpl extends ServiceImpl<HotelMemberLevelPayDao, HotelMemberLevelPayEntity> implements HotelMemberLevelPayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelMemberLevelPayEntity> page = this.page(
                new Query<HotelMemberLevelPayEntity>().getPage(params),
                new QueryWrapper<HotelMemberLevelPayEntity>()
        );

        return new PageUtils(page);
    }

}