package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRechargeDao;
import io.renren.modules.hotel.entity.HotelRechargeEntity;
import io.renren.modules.hotel.service.HotelRechargeService;

@Service("hotelRechargeService")
public class HotelRechargeServiceImpl extends ServiceImpl<HotelRechargeDao, HotelRechargeEntity> implements HotelRechargeService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRechargeEntity> page = this.page(new Query<HotelRechargeEntity>().getPage(params), new QueryWrapper<HotelRechargeEntity>());

		return new PageUtils(page);
	}

}