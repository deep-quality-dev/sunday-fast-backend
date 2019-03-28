package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelDistributionDao;
import io.renren.modules.hotel.entity.HotelDistributionEntity;
import io.renren.modules.hotel.service.HotelDistributionService;

@Service("hotelDistributionService")
public class HotelDistributionServiceImpl extends ServiceImpl<HotelDistributionDao, HotelDistributionEntity> implements HotelDistributionService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelDistributionEntity> page = this.page(new Query<HotelDistributionEntity>().getPage(params), new QueryWrapper<HotelDistributionEntity>());

		return new PageUtils(page);
	}

}