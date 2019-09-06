package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Page;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomDao;
import io.renren.modules.hotel.dao.HotelRoomMoneyDao;
import io.renren.modules.hotel.dao.HotelRoomPriceDao;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.service.HotelRoomPriceService;
import io.renren.modules.hotel.vo.RoomPriceVo;

@Service("hotelRoomPriceService")
public class HotelRoomPriceServiceImpl extends ServiceImpl<HotelRoomPriceDao, HotelRoomPriceEntity> implements HotelRoomPriceService {

	@Autowired
	private HotelRoomDao hotelRoomDao;

	@Autowired
	private HotelRoomMoneyDao hotelRoomMoneyDao;

	@Autowired
	private HotelRoomPriceDao hotelRoomPriceDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRoomPriceEntity> page = this.page(new Query<HotelRoomPriceEntity>().getPage(params), new QueryWrapper<HotelRoomPriceEntity>());

		return new PageUtils(page);
	}

	@Override
	public RoomPriceVo roomPrice(Long sellerId, String startDate, String endDate, Page page) {
		RoomPriceVo roomPriceVo = new RoomPriceVo();
		// 加载区间日期
		List<String> dates = findDates(startDate, endDate);
		roomPriceVo.setDate(dates);
		// 商家所有房型
		List<Map<String, Object>> roomDataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> roomData = null;
		HotelRoomPriceEntity roomPriceEntity = null;
		List<HotelRoomEntity> hotelRoomEntities = hotelRoomDao.selectList(Wrappers.<HotelRoomEntity>lambdaQuery().eq(HotelRoomEntity::getSellerId, sellerId).eq(HotelRoomEntity::getState, 1));
		for (HotelRoomEntity hotelRoomEntity : hotelRoomEntities) {
			List<HotelRoomMoneyEntity> hotelRoomMoneyEntities = hotelRoomMoneyDao.selectList(Wrappers.<HotelRoomMoneyEntity>lambdaQuery().eq(HotelRoomMoneyEntity::getRoomId, hotelRoomEntity.getId()));
			for (HotelRoomMoneyEntity hotelRoomMoneyEntity : hotelRoomMoneyEntities) {
				roomData = new HashMap<String, Object>();
				roomData.put("roomName", hotelRoomEntity.getName());
				roomData.put("roomId", hotelRoomEntity.getId());
				roomData.put("priceName", hotelRoomMoneyEntity.getName());
				roomData.put("priceId", hotelRoomMoneyEntity.getId());
				for (String day : dates) {
					roomData.put(day, hotelRoomMoneyEntity.getPrice());
					// 查询特殊价格
					roomPriceEntity = hotelRoomPriceDao.selectOne(Wrappers.<HotelRoomPriceEntity>lambdaQuery().eq(HotelRoomPriceEntity::getMoneyId, hotelRoomMoneyEntity.getId()).eq(HotelRoomPriceEntity::getRoomdate, DateUtil.parse(day).getTime()));
					if (null != roomPriceEntity) {
						roomData.put(day, roomPriceEntity.getOprice());
					}
				}
				roomDataList.add(roomData);
			}
		}
		for (Map<String, Object> roomPrice : roomDataList) {
			
		}
		roomPriceVo.setDataList(roomDataList);
		return roomPriceVo;
	}

	public List<String> findDates(String dBegin, String dEnd) {
		List<String> lDate = new ArrayList<String>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(DateUtil.parse(dBegin));
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(DateUtil.parse(dEnd));
		// 测试此日期是否在指定日期之后
		while (DateUtil.parse(dEnd).after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(DateUtil.formatDate(calBegin.getTime()));
		}
		return lDate;
	}

	public static void main(String[] args) {
		System.out.println(DateTime.now().getTime());
	}

}