package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelWithdrawalDao;
import io.renren.modules.hotel.entity.HotelWithdrawalEntity;
import io.renren.modules.hotel.service.HotelWithdrawalService;

@Service("hotelWithdrawalService")
public class HotelWithdrawalServiceImpl extends ServiceImpl<HotelWithdrawalDao, HotelWithdrawalEntity> implements HotelWithdrawalService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelWithdrawalEntity> page = this.page(new Query<HotelWithdrawalEntity>().getPage(params), new QueryWrapper<HotelWithdrawalEntity>());

		return new PageUtils(page);
	}

}