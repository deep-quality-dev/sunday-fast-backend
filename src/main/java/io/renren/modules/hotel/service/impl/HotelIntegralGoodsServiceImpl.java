package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelIntegralGoodsDao;
import io.renren.modules.hotel.entity.HotelIntegralGoodsEntity;
import io.renren.modules.hotel.service.HotelIntegralGoodsService;

@Service("hotelIntegralGoodsService")
public class HotelIntegralGoodsServiceImpl extends ServiceImpl<HotelIntegralGoodsDao, HotelIntegralGoodsEntity> implements HotelIntegralGoodsService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelIntegralGoodsEntity> page = this.page(new Query<HotelIntegralGoodsEntity>().getPage(params), new QueryWrapper<HotelIntegralGoodsEntity>());

		return new PageUtils(page);
	}

}