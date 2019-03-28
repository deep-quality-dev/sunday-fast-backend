package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelIntegralClassifyDao;
import io.renren.modules.hotel.entity.HotelIntegralClassifyEntity;
import io.renren.modules.hotel.service.HotelIntegralClassifyService;

@Service("hotel integralClassifyService")
public class HotelIntegralClassifyServiceImpl extends ServiceImpl<HotelIntegralClassifyDao, HotelIntegralClassifyEntity> implements HotelIntegralClassifyService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelIntegralClassifyEntity> page = this.page(new Query<HotelIntegralClassifyEntity>().getPage(params), new QueryWrapper<HotelIntegralClassifyEntity>());

		return new PageUtils(page);
	}

}