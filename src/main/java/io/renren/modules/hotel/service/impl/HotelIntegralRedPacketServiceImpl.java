package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelIntegralRedPacketDao;
import io.renren.modules.hotel.entity.HotelIntegralRedPacketEntity;
import io.renren.modules.hotel.service.HotelIntegralRedPacketService;

@Service("hotelIntegralRedPacketService")
public class HotelIntegralRedPacketServiceImpl extends ServiceImpl<HotelIntegralRedPacketDao, HotelIntegralRedPacketEntity> implements HotelIntegralRedPacketService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelIntegralRedPacketEntity> page = this.page(new Query<HotelIntegralRedPacketEntity>().getPage(params), new QueryWrapper<HotelIntegralRedPacketEntity>());

		return new PageUtils(page);
	}

}