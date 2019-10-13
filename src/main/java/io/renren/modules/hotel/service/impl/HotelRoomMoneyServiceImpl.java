package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomMoneyDao;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomService;

@Service("hotelRoomMoneyService")
public class HotelRoomMoneyServiceImpl extends ServiceImpl<HotelRoomMoneyDao, HotelRoomMoneyEntity> implements HotelRoomMoneyService {

	@Autowired
	private HotelRoomService hotelRoomService;

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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRoomMoney(HotelRoomMoneyEntity hotelRoomMoney) {
		this.save(hotelRoomMoney);
		// 更新最低房价到房型上面
		List<HotelRoomMoneyEntity> hotelRoomMoneyEntities = this.list(Wrappers.<HotelRoomMoneyEntity>lambdaQuery().eq(HotelRoomMoneyEntity::getRoomId, hotelRoomMoney.getRoomId()));
		List<Double> list = hotelRoomMoneyEntities.stream().map((HotelRoomMoneyEntity item) -> {
			return item.getPrice().doubleValue();
		}).collect(Collectors.toList());
		double minVal = Collections.min(list);
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelRoomMoney.getRoomId());
		hotelRoomEntity.setPrice(new BigDecimal(minVal));
		hotelRoomService.updateById(hotelRoomEntity);
	}

}