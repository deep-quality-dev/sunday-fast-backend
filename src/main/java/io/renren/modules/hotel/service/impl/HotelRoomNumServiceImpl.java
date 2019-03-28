package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomNumDao;
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
import io.renren.modules.hotel.service.HotelRoomNumService;

@Service("hotelRoomNumService")
public class HotelRoomNumServiceImpl extends ServiceImpl<HotelRoomNumDao, HotelRoomNumEntity> implements HotelRoomNumService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRoomNumEntity> page = this.page(new Query<HotelRoomNumEntity>().getPage(params), new QueryWrapper<HotelRoomNumEntity>());

		return new PageUtils(page);
	}

}