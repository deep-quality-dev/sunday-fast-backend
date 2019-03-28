package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelNavDao;
import io.renren.modules.hotel.entity.HotelNavEntity;
import io.renren.modules.hotel.service.HotelNavService;

@Service("hotelNavService")
public class HotelNavServiceImpl extends ServiceImpl<HotelNavDao, HotelNavEntity> implements HotelNavService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelNavEntity> page = this.page(new Query<HotelNavEntity>().getPage(params), new QueryWrapper<HotelNavEntity>());

		return new PageUtils(page);
	}

}