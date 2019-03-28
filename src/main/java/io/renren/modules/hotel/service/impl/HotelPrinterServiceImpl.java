package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelPrinterDao;
import io.renren.modules.hotel.entity.HotelPrinterEntity;
import io.renren.modules.hotel.service.HotelPrinterService;

@Service("hotelPrinterService")
public class HotelPrinterServiceImpl extends ServiceImpl<HotelPrinterDao, HotelPrinterEntity> implements HotelPrinterService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelPrinterEntity> page = this.page(new Query<HotelPrinterEntity>().getPage(params), new QueryWrapper<HotelPrinterEntity>());

		return new PageUtils(page);
	}

}