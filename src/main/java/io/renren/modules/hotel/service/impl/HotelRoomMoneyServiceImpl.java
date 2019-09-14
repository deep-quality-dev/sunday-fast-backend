package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomMoneyDao;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;

@Service("hotelRoomMoneyService")
public class HotelRoomMoneyServiceImpl extends ServiceImpl<HotelRoomMoneyDao, HotelRoomMoneyEntity> implements HotelRoomMoneyService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRoomMoneyEntity> page = this.page(new Query<HotelRoomMoneyEntity>().getPage(params), new QueryWrapper<HotelRoomMoneyEntity>().eq("room_id", params.get("roomId")));

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRoomNum(HotelRoomMoneyEntity hotelRoomMoneyEntity, int roomNum) {
		baseMapper.updateRoomNum(hotelRoomMoneyEntity, roomNum);
	}

}