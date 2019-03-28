package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelMemberCouponsDao;
import io.renren.modules.hotel.entity.HotelMemberCouponsEntity;
import io.renren.modules.hotel.service.HotelMemberCouponsService;

@Service("hotelMemberCouponsService")
public class HotelMemberCouponsServiceImpl extends ServiceImpl<HotelMemberCouponsDao, HotelMemberCouponsEntity> implements HotelMemberCouponsService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelMemberCouponsEntity> page = this.page(new Query<HotelMemberCouponsEntity>().getPage(params), new QueryWrapper<HotelMemberCouponsEntity>());

		return new PageUtils(page);
	}

}