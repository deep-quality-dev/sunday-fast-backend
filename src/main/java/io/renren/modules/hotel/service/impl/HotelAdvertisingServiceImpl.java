package io.renren.modules.hotel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelAdvertisingDao;
import io.renren.modules.hotel.entity.HotelAdvertisingEntity;
import io.renren.modules.hotel.service.HotelAdvertisingService;

@Service("hotelAdvertisingService")
public class HotelAdvertisingServiceImpl extends ServiceImpl<HotelAdvertisingDao, HotelAdvertisingEntity> implements HotelAdvertisingService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelAdvertisingEntity> page = this.page(new Query<HotelAdvertisingEntity>().getPage(params), new QueryWrapper<HotelAdvertisingEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<HotelAdvertisingEntity> loadByType(Long sellerId, int type) {
		return this.list(new QueryWrapper<HotelAdvertisingEntity>().eq("seller_id", sellerId).eq("type", type));
	}

}