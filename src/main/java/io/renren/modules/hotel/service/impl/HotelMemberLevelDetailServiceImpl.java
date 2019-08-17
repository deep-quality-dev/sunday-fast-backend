package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelMemberLevelDetailDao;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;


@Service("hotelMemberLevelDetailService")
public class HotelMemberLevelDetailServiceImpl extends ServiceImpl<HotelMemberLevelDetailDao, HotelMemberLevelDetailEntity> implements HotelMemberLevelDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelMemberLevelDetailEntity> page = this.page(
                new Query<HotelMemberLevelDetailEntity>().getPage(params),
                new QueryWrapper<HotelMemberLevelDetailEntity>()
        );

        return new PageUtils(page);
    }

}