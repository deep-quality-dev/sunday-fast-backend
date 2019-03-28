package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelIntegralRecordDao;
import io.renren.modules.hotel.entity.HotelIntegralRecordEntity;
import io.renren.modules.hotel.service.HotelIntegralRecordService;

@Service("hotelIntegralRecordService")
public class HotelIntegralRecordServiceImpl extends ServiceImpl<HotelIntegralRecordDao, HotelIntegralRecordEntity> implements HotelIntegralRecordService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelIntegralRecordEntity> page = this.page(new Query<HotelIntegralRecordEntity>().getPage(params), new QueryWrapper<HotelIntegralRecordEntity>());

		return new PageUtils(page);
	}

}