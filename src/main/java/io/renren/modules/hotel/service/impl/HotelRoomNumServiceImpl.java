package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Page;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomDao;
import io.renren.modules.hotel.dao.HotelRoomMoneyDao;
import io.renren.modules.hotel.dao.HotelRoomNumDao;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
import io.renren.modules.hotel.service.HotelRoomNumService;
import io.renren.modules.hotel.vo.RoomNumVo;

@Service("hotelRoomNumService")
public class HotelRoomNumServiceImpl extends ServiceImpl<HotelRoomNumDao, HotelRoomNumEntity> implements HotelRoomNumService {

	@Autowired
	private HotelRoomDao hotelRoomDao;

	@Autowired
	private HotelRoomNumDao hotelRoomNumDao;

	@Autowired
	private HotelRoomMoneyDao hotelRoomMoneyDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRoomNumEntity> page = this.page(new Query<HotelRoomNumEntity>().getPage(params), new QueryWrapper<HotelRoomNumEntity>());

		return new PageUtils(page);
	}

	@Override
	public RoomNumVo roomNumData(Long sellerId, String startDate, String endDate, Page page) {
		RoomNumVo roomNumVo = new RoomNumVo();
		// 加载区间日期
		List<String> dates = findDates(startDate, endDate);
		roomNumVo.setDate(dates);
		// 商家所有房型
		List<Map<String, Object>> roomDataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> roomData = null;
		HotelRoomNumEntity roomNumEntity = null;
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
					roomData.put(day, hotelRoomMoneyEntity.getNum());
					// 查询指定日期房量
					roomNumEntity = hotelRoomNumDao.selectOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getMoneyId, hotelRoomMoneyEntity.getId()).eq(HotelRoomNumEntity::getDateday, DateUtil.parse(day).getTime()));
					if (null != roomNumEntity) {
						roomData.put(day, roomNumEntity.getNums());
					}
				}
				roomDataList.add(roomData);
			}
		}
		roomNumVo.setDataList(roomDataList);
		return roomNumVo;
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update4Day(Map<String, Object> params) {
		String date = params.get("date").toString();
		Object priceId = params.get("priceId");
		String num = params.get("num").toString();
		HotelRoomNumEntity roomNumEntity = hotelRoomNumDao.selectOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getMoneyId, priceId).eq(HotelRoomNumEntity::getDateday, DateUtil.parse(date).getTime()));
		if (null == roomNumEntity) {
			HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyDao.selectById(Long.valueOf(priceId.toString()));
			roomNumEntity = new HotelRoomNumEntity();
			roomNumEntity.setNums(Integer.valueOf(num));
			roomNumEntity.setRid(hotelRoomMoneyEntity.getRoomId());
			roomNumEntity.setDateday(DateUtil.parse(date).getTime());
			roomNumEntity.setMoneyId(Long.valueOf(priceId.toString()));
			hotelRoomNumDao.insert(roomNumEntity);
			return;
		}
		roomNumEntity.setNums(Integer.valueOf(num));
		hotelRoomNumDao.updateById(roomNumEntity);
	}

}