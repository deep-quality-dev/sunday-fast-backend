package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelOrderSettingRoomDao;
import io.renren.modules.hotel.entity.HotelOrderSettingRoomEntity;
import io.renren.modules.hotel.service.HotelOrderSettingRoomService;


@Service("hotelOrderSettingRoomService")
public class HotelOrderSettingRoomServiceImpl extends ServiceImpl<HotelOrderSettingRoomDao, HotelOrderSettingRoomEntity> implements HotelOrderSettingRoomService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelOrderSettingRoomEntity> page = this.page(
                new Query<HotelOrderSettingRoomEntity>().getPage(params),
                new QueryWrapper<HotelOrderSettingRoomEntity>()
        );

        return new PageUtils(page);
    }

}