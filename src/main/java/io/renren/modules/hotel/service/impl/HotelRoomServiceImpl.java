package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelRoomDao;
import io.renren.modules.hotel.dao.HotelRoomNumDao;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
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

	@Autowired
	private HotelRoomNumDao hotelRoomNumDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelRoomEntity> page = this.page(new Query<HotelRoomEntity>().getPage(params), new QueryWrapper<HotelRoomEntity>().eq(sellerId != null, "seller_id", sellerId));
		return new PageUtils(page);
	}

	@Override
	public List<RoomVO> roomList(Long sellerId, String startTime, String endTime) {
		log.info("获取酒店房型列表--start，sellerId:{},startTime:{},endTime:{}", sellerId, startTime, endTime);
		List<HotelRoomEntity> hotelRoomEntities = this.list(new QueryWrapper<HotelRoomEntity>().eq("seller_id", sellerId));
		List<RoomVO> roomVOs = hotelRoomEntities.stream().map((HotelRoomEntity item) -> {
			RoomVO roomVO = new RoomVO();
			BeanUtil.copyProperties(item, roomVO);
			roomVO.setPrice(NumberUtil.decimalFormat("0.00", item.getPrice().doubleValue()));
			// 获取房价列表
			List<RoomMoneyVo> roomMoneyVos = this.roomMoneys(item.getId(), DateUtil.parse(startTime), DateUtil.parse(endTime));
			roomVO.setAmountItems(roomMoneyVos);
			return roomVO;
		}).collect(Collectors.toList());
		log.info("获取酒店房型列表--end,result:{}", JSON.toJSONString(roomVOs));
		return roomVOs;
	}

	@Override
	public List<RoomMoneyVo> roomMoneys(Long roomId, Date startTime, Date endTime) {
		log.debug("查询酒店房价列表--start，roomId:{},startTime:{},endTime:{}", roomId, startTime, endTime);
		List<RoomMoneyVo> roomMoneyVos = new ArrayList<RoomMoneyVo>();
		List<HotelRoomMoneyEntity> moneyEntities = hotelRoomMoneyService.list(new QueryWrapper<HotelRoomMoneyEntity>().eq("room_id", roomId).eq("status", 1));
		roomMoneyVos = moneyEntities.stream().map((HotelRoomMoneyEntity item) -> {
			RoomMoneyVo roomMoneyVo = new RoomMoneyVo();
			// 先set会员价格
			roomMoneyVo.setAmount(item.getPrice());
			roomMoneyVo.setId(item.getId());
			roomMoneyVo.setName(item.getName());
			roomMoneyVo.setVipPrice(item.getIsVip());
			roomMoneyVo.setHasRoom(item.getNum());
			// 查询特殊房量
			HotelRoomNumEntity hotelRoomNumEntity = hotelRoomNumDao.selectOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getRid, item.getId()).eq(HotelRoomNumEntity::getDateday, startTime.getTime()).eq(HotelRoomNumEntity::getMoneyId, item.getId()));
			if (null != hotelRoomNumEntity) {
				roomMoneyVo.setHasRoom(hotelRoomNumEntity.getNums());
			}
			// 查询是否有设置特殊价格
			log.debug("查询特殊房价--start，moneyId:{},roomId:{},date:{}", item.getId(), roomId, startTime.getTime());
			HotelRoomPriceEntity hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("money_id", item.getId()).eq("room_id", roomId).eq("roomdate", startTime.getTime()));
			log.debug("查询特殊房价--end,result:{}", JSON.toJSONString(hotelRoomPriceEntity));
			if (null != hotelRoomPriceEntity) {
				// 先set会员价格
				roomMoneyVo.setAmount(hotelRoomPriceEntity.getMprice());
			}
			return roomMoneyVo;
		}).collect(Collectors.toList());
		log.info("获取酒店房型列表--end,result:{}", JSON.toJSONString(roomMoneyVos));
		return roomMoneyVos;
	}

}