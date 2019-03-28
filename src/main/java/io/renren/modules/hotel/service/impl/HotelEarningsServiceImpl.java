package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelEarningsDao;
import io.renren.modules.hotel.entity.HotelEarningsEntity;
import io.renren.modules.hotel.service.HotelEarningsService;

@Service("hotelEarningsService")
public class HotelEarningsServiceImpl extends ServiceImpl<HotelEarningsDao, HotelEarningsEntity> implements HotelEarningsService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelEarningsEntity> page = this.page(new Query<HotelEarningsEntity>().getPage(params), new QueryWrapper<HotelEarningsEntity>());

		return new PageUtils(page);
	}

}