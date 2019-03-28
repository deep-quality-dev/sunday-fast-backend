package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelDistributionSetDao;
import io.renren.modules.hotel.entity.HotelDistributionSetEntity;
import io.renren.modules.hotel.service.HotelDistributionSetService;

@Service("hotelDistributionSetService")
public class HotelDistributionSetServiceImpl extends ServiceImpl<HotelDistributionSetDao, HotelDistributionSetEntity> implements HotelDistributionSetService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelDistributionSetEntity> page = this.page(new Query<HotelDistributionSetEntity>().getPage(params), new QueryWrapper<HotelDistributionSetEntity>());

		return new PageUtils(page);
	}

}