package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelAssessDao;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.service.HotelAssessService;

@Service("hotelAssessService")
public class HotelAssessServiceImpl extends ServiceImpl<HotelAssessDao, HotelAssessEntity> implements HotelAssessService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelAssessEntity> page = this.page(new Query<HotelAssessEntity>().getPage(params), new QueryWrapper<HotelAssessEntity>());

		return new PageUtils(page);
	}

}