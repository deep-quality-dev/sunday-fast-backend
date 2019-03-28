package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelSystemDao;
import io.renren.modules.hotel.entity.HotelSystemEntity;
import io.renren.modules.hotel.service.HotelSystemService;

@Service("hotelSystemService")
public class HotelSystemServiceImpl extends ServiceImpl<HotelSystemDao, HotelSystemEntity> implements HotelSystemService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelSystemEntity> page = this.page(new Query<HotelSystemEntity>().getPage(params), new QueryWrapper<HotelSystemEntity>());

		return new PageUtils(page);
	}

}