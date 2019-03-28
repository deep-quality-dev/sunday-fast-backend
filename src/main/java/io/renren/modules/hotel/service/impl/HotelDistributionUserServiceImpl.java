package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelDistributionUserDao;
import io.renren.modules.hotel.entity.HotelDistributionUserEntity;
import io.renren.modules.hotel.service.HotelDistributionUserService;

@Service("hotelDistributionUserService")
public class HotelDistributionUserServiceImpl extends ServiceImpl<HotelDistributionUserDao, HotelDistributionUserEntity> implements HotelDistributionUserService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelDistributionUserEntity> page = this.page(new Query<HotelDistributionUserEntity>().getPage(params), new QueryWrapper<HotelDistributionUserEntity>());

		return new PageUtils(page);
	}

}