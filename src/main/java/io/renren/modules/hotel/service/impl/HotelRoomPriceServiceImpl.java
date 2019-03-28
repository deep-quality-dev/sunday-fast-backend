package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomPriceDao;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.service.HotelRoomPriceService;

@Service("hotelRoomPriceService")
public class HotelRoomPriceServiceImpl extends ServiceImpl<HotelRoomPriceDao, HotelRoomPriceEntity> implements HotelRoomPriceService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRoomPriceEntity> page = this.page(new Query<HotelRoomPriceEntity>().getPage(params), new QueryWrapper<HotelRoomPriceEntity>());

		return new PageUtils(page);
	}

}