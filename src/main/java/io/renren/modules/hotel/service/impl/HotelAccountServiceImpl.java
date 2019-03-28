package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelAccountDao;
import io.renren.modules.hotel.entity.HotelAccountEntity;
import io.renren.modules.hotel.service.HotelAccountService;

@Service("hotelAccountService")
public class HotelAccountServiceImpl extends ServiceImpl<HotelAccountDao, HotelAccountEntity> implements HotelAccountService {
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelAccountEntity> page = this.page(new Query<HotelAccountEntity>().getPage(params), new QueryWrapper<HotelAccountEntity>());

		return new PageUtils(page);
	}

}