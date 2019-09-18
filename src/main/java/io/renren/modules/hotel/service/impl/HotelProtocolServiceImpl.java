package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelProtocolDao;
import io.renren.modules.hotel.entity.HotelProtocolEntity;
import io.renren.modules.hotel.service.HotelProtocolService;


@Service("hotelProtocolService")
public class HotelProtocolServiceImpl extends ServiceImpl<HotelProtocolDao, HotelProtocolEntity> implements HotelProtocolService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelProtocolEntity> page = this.page(
                new Query<HotelProtocolEntity>().getPage(params),
                new QueryWrapper<HotelProtocolEntity>()
        );

        return new PageUtils(page);
    }

}