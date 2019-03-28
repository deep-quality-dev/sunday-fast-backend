package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelCommissionWithdrawalDao;
import io.renren.modules.hotel.entity.HotelCommissionWithdrawalEntity;
import io.renren.modules.hotel.service.HotelCommissionWithdrawalService;

@Service("hotelCommissionWithdrawalService")
public class HotelCommissionWithdrawalServiceImpl extends ServiceImpl<HotelCommissionWithdrawalDao, HotelCommissionWithdrawalEntity> implements HotelCommissionWithdrawalService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelCommissionWithdrawalEntity> page = this.page(new Query<HotelCommissionWithdrawalEntity>().getPage(params), new QueryWrapper<HotelCommissionWithdrawalEntity>());

		return new PageUtils(page);
	}

}