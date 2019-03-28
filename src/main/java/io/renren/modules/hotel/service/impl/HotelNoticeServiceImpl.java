package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelNoticeDao;
import io.renren.modules.hotel.entity.HotelNoticeEntity;
import io.renren.modules.hotel.service.HotelNoticeService;

@Service("hotelNoticeService")
public class HotelNoticeServiceImpl extends ServiceImpl<HotelNoticeDao, HotelNoticeEntity> implements HotelNoticeService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelNoticeEntity> page = this.page(new Query<HotelNoticeEntity>().getPage(params), new QueryWrapper<HotelNoticeEntity>());

		return new PageUtils(page);
	}

}