package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelCzhdDao;
import io.renren.modules.hotel.entity.HotelCzhdEntity;
import io.renren.modules.hotel.service.HotelCzhdService;

@Service("hotelCzhdService")
public class HotelCzhdServiceImpl extends ServiceImpl<HotelCzhdDao, HotelCzhdEntity> implements HotelCzhdService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelCzhdEntity> page = this.page(new Query<HotelCzhdEntity>().getPage(params), new QueryWrapper<HotelCzhdEntity>());

		return new PageUtils(page);
	}

}