package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomDao;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomPriceService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.vo.RoomMoneyVo;
import io.renren.modules.hotel.vo.RoomVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelRoomService")
public class HotelRoomServiceImpl extends ServiceImpl<HotelRoomDao, HotelRoomEntity> implements HotelRoomService {

	@Autowired
	private HotelRoomMoneyService hotelRoomMoneyService;

	@Autowired
	private HotelRoomPriceService hotelRoomPriceService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRoomEntity> page = this.page(new Query<HotelRoomEntity>().getPage(params), new QueryWrapper<HotelRoomEntity>());
		return new PageUtils(page);
	}

	@Override
	public List<RoomVO> roomList(Long sellerId, String startTime, String endTime) {
		log.info("获取酒店房型列表--start，sellerId:{},startTime:{},endTime:{}", sellerId, startTime, endTime);
		List<RoomVO> roomVOs = new ArrayList<RoomVO>();
		List<HotelRoomEntity> hotelRoomEntities = this.list(new QueryWrapper<HotelRoomEntity>().eq("seller_id", sellerId));
		RoomVO roomVO = null;
		for (HotelRoomEntity hotelRoomEntity : hotelRoomEntities) {
			roomVO = new RoomVO();
			BeanUtil.copyProperties(hotelRoomEntity, roomVO);
			// 获取房价列表
			List<RoomMoneyVo> roomMoneyVos = this.roomMoneys(hotelRoomEntity.getId(), DateUtil.parse(startTime), DateUtil.parse(endTime));
			roomVO.setAmountItems(roomMoneyVos);
			roomVOs.add(roomVO);
		}
		log.info("获取酒店房型列表--end,result:{}", JSON.toJSONString(roomVOs));
		return roomVOs;
	}

	@Override
	public List<RoomMoneyVo> roomMoneys(Long roomId, Date startTime, Date endTime) {
		log.info("查询酒店房价列表--start，roomId:{},startTime:{},endTime:{}", roomId, startTime, endTime);
		List<RoomMoneyVo> roomMoneyVos = new ArrayList<RoomMoneyVo>();
		RoomMoneyVo roomMoneyVo = null;
		List<HotelRoomMoneyEntity> moneyEntities = hotelRoomMoneyService.list(new QueryWrapper<HotelRoomMoneyEntity>().eq("room_id", roomId).eq("status", 1));
		for (HotelRoomMoneyEntity hotelRoomMoneyEntity : moneyEntities) {
			roomMoneyVo = new RoomMoneyVo();
			// 先set会员价格
			roomMoneyVo.setAmount(hotelRoomMoneyEntity.getMprice());
			roomMoneyVo.setId(hotelRoomMoneyEntity.getId());
			roomMoneyVo.setName(hotelRoomMoneyEntity.getMotitle());
			// 查询是否有设置特殊价格
			log.info("查询特殊房价--start，moneyId:{},roomId:{},date:{}", hotelRoomMoneyEntity.getId(), roomId, startTime.getSeconds());
			HotelRoomPriceEntity hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("money_id", hotelRoomMoneyEntity.getId()).eq("room_id", roomId).eq("roomdate", startTime.getSeconds()));
			log.info("查询特殊房价--end,result:{}", JSON.toJSONString(hotelRoomPriceEntity));
			if (null != hotelRoomPriceEntity) {
				// 先set会员价格
				roomMoneyVo.setAmount(hotelRoomPriceEntity.getMprice());
			}
			roomMoneyVos.add(roomMoneyVo);
		}
		log.info("获取酒店房型列表--end,result:{}", JSON.toJSONString(roomMoneyVos));
		return roomMoneyVos;
	}

}