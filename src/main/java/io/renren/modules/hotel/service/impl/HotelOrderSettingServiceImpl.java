package io.renren.modules.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollectionUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelOrderSettingDao;
import io.renren.modules.hotel.entity.HotelOrderSettingDateEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingRoomEntity;
import io.renren.modules.hotel.service.HotelOrderSettingDateService;
import io.renren.modules.hotel.service.HotelOrderSettingRoomService;
import io.renren.modules.hotel.service.HotelOrderSettingService;

@Service("hotelOrderSettingService")
public class HotelOrderSettingServiceImpl extends ServiceImpl<HotelOrderSettingDao, HotelOrderSettingEntity> implements HotelOrderSettingService {

	@Autowired
	private HotelOrderSettingRoomService hotelOrderSettingRoomService;

	@Autowired
	private HotelOrderSettingDateService hotelOrderSettingDateService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelOrderSettingEntity> page = this.page(new Query<HotelOrderSettingEntity>().getPage(params), new QueryWrapper<HotelOrderSettingEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrderSetting(HotelOrderSettingEntity hotelOrderSetting) {
		baseMapper.insert(hotelOrderSetting);
		// 保存适用房型
		List<HotelOrderSettingRoomEntity> rooms = hotelOrderSetting.getRooms();
		if (CollectionUtil.isNotEmpty(rooms)) {
			for (HotelOrderSettingRoomEntity hotelOrderSettingRoomEntity : rooms) {
				hotelOrderSettingRoomEntity.setSettingId(hotelOrderSetting.getId());
				hotelOrderSettingRoomService.save(hotelOrderSettingRoomEntity);
			}
		}
		List<HotelOrderSettingDateEntity> days = hotelOrderSetting.getDays();
		if (CollectionUtil.isNotEmpty(days)) {
			for (HotelOrderSettingDateEntity hotelOrderSettingDateEntity : days) {
				hotelOrderSettingDateEntity.setSettingId(hotelOrderSetting.getId());
				hotelOrderSettingDateService.save(hotelOrderSettingDateEntity);
			}
		}
	}

	@Override
	public void updateOrderSetting(HotelOrderSettingEntity hotelOrderSetting) {
		this.saveOrUpdate(hotelOrderSetting);
		// 保存适用房型
		List<HotelOrderSettingRoomEntity> rooms = hotelOrderSetting.getRooms();
		if (CollectionUtil.isNotEmpty(rooms)) {
			hotelOrderSettingRoomService.remove(Wrappers.<HotelOrderSettingRoomEntity>lambdaQuery().eq(HotelOrderSettingRoomEntity::getSettingId, hotelOrderSetting.getId()));
			for (HotelOrderSettingRoomEntity hotelOrderSettingRoomEntity : rooms) {
				hotelOrderSettingRoomEntity.setSettingId(hotelOrderSetting.getId());
				hotelOrderSettingRoomService.save(hotelOrderSettingRoomEntity);
			}
		}
		List<HotelOrderSettingDateEntity> days = hotelOrderSetting.getDays();
		if (CollectionUtil.isNotEmpty(days)) {
			hotelOrderSettingDateService.remove(Wrappers.<HotelOrderSettingDateEntity>lambdaQuery().eq(HotelOrderSettingDateEntity::getSettingId, hotelOrderSetting.getId()));
			for (HotelOrderSettingDateEntity hotelOrderSettingDateEntity : days) {
				hotelOrderSettingDateEntity.setSettingId(hotelOrderSetting.getId());
				hotelOrderSettingDateService.save(hotelOrderSettingDateEntity);
			}
		}

	}

}