package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelMemberLevelDetailDao;
import io.renren.modules.hotel.dao.HotelRoomDao;
import io.renren.modules.hotel.dao.HotelRoomNumDao;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomPriceService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.vo.RoomMoneyVo;
import io.renren.modules.hotel.vo.RoomVO;
import io.renren.modules.hotel.vo.RoomVipMoneyVo;
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

	@Autowired
	private HotelMemberLevelDao hotelMemberLevelDao;

	@Autowired
	private HotelMemberLevelDetailDao hotelMemberLevelDetailDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelRoomEntity> page = this.page(new Query<HotelRoomEntity>().getPage(params), new QueryWrapper<HotelRoomEntity>().eq(sellerId != null, "seller_id", sellerId));
		return new PageUtils(page);
	}

	@Override
	public List<RoomVO> roomList(Long userId, Long sellerId, String startTime, String endTime, int roomType) {
		log.info("获取酒店房型列表--start，sellerId:{},startTime:{},endTime:{}", sellerId, startTime, endTime);
		// 商家会员列表
		List<HotelMemberLevelEntity> hotelMemberLevelEntities = hotelMemberLevelDao.selectList(Wrappers.<HotelMemberLevelEntity>lambdaQuery().eq(HotelMemberLevelEntity::getSellerId, sellerId).orderByAsc(HotelMemberLevelEntity::getLevel));
		// 用户酒店会员
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailDao.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		Long levelId = 0L;
		if (null != hotelMemberLevelDetailEntity) {
			levelId = hotelMemberLevelDetailEntity.getLevelId();
		}
		HotelMemberLevelEntity memberLevelEntity = hotelMemberLevelDao.selectById(levelId);
		List<HotelRoomEntity> hotelRoomEntities = this.list(Wrappers.<HotelRoomEntity>lambdaQuery().eq(HotelRoomEntity::getSellerId, sellerId).eq(HotelRoomEntity::getClassify, roomType));
		List<RoomVO> roomVOs = hotelRoomEntities.stream().map((HotelRoomEntity item) -> {
			RoomVO roomVO = new RoomVO();
			BeanUtil.copyProperties(item, roomVO);
			roomVO.setPrice(NumberUtil.decimalFormat("0.00", item.getPrice().doubleValue()));
			// 获取房价列表
			List<RoomMoneyVo> roomMoneyVos = this.roomMoneys(memberLevelEntity, hotelMemberLevelEntities, item.getId(), DateUtil.parse(startTime), DateUtil.parse(endTime));
			roomVO.setAmountItems(roomMoneyVos);
			// 日期区间是否有满房情况
			int result = hotelRoomNumDao.selectCount(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getRid, item.getId()).between(HotelRoomNumEntity::getDateday, DateUtil.parse(startTime).getTime(), DateUtil.parse(endTime).getTime()).lt(HotelRoomNumEntity::getNums, 1));
			roomVO.setHasRoom(result == 0);
			return roomVO;
		}).collect(Collectors.toList());
		log.debug("获取酒店房型列表--end,result:{}", JSON.toJSONString(roomVOs));
		return roomVOs;
	}

	@Override
	public List<RoomMoneyVo> roomMoneys(HotelMemberLevelEntity memberLevelEntity, List<HotelMemberLevelEntity> hotelMemberLevelEntities, Long roomId, Date startTime, Date endTime) {
		log.debug("查询酒店房价列表--start，roomId:{},startTime:{},endTime:{}", roomId, startTime, endTime);
		List<RoomMoneyVo> roomMoneyVos = new ArrayList<RoomMoneyVo>();
		List<HotelRoomMoneyEntity> moneyEntities = hotelRoomMoneyService.list(new QueryWrapper<HotelRoomMoneyEntity>().eq("room_id", roomId).eq("status", 1));
		roomMoneyVos = moneyEntities.stream().map((HotelRoomMoneyEntity item) -> {
			RoomMoneyVo roomMoneyVo = new RoomMoneyVo();
			// 先set会员价格
			BigDecimal amount = item.getPrice();
			roomMoneyVo.setAmount(item.getPrice());
			roomMoneyVo.setId(item.getId());
			roomMoneyVo.setName(item.getName());
			roomMoneyVo.setVipPrice(item.getIsVip());
			roomMoneyVo.setPrepay(item.getPrepay());
			roomMoneyVo.setHasRoom(item.getNum());
			// 查询特殊房量
			HotelRoomNumEntity hotelRoomNumEntity = hotelRoomNumDao.selectOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getRid, item.getRoomId()).eq(HotelRoomNumEntity::getDateday, startTime.getTime()).eq(HotelRoomNumEntity::getMoneyId, item.getId()));
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
				amount = hotelRoomPriceEntity.getMprice();
			}
			// 生成会员价格列表
			if (null != memberLevelEntity) {
				roomMoneyVo.setAmount(NumberUtil.mul(amount, memberLevelEntity.getDiscount()));
			}
			if (item.getIsVip() == 1) {
				List<RoomVipMoneyVo> vipPriceList = new ArrayList<RoomVipMoneyVo>();
				RoomVipMoneyVo roomVipMoneyVo = null;
				for (HotelMemberLevelEntity hotelMemberLevelEntity : hotelMemberLevelEntities) {
					roomVipMoneyVo = new RoomVipMoneyVo();
					roomVipMoneyVo.setIcon(hotelMemberLevelEntity.getIcon());
					roomVipMoneyVo.setAmount(NumberUtil.decimalFormat("0.00", NumberUtil.mul(amount, hotelMemberLevelEntity.getDiscount()).doubleValue()));
					roomVipMoneyVo.setName(hotelMemberLevelEntity.getName());
					vipPriceList.add(roomVipMoneyVo);
				}
				roomMoneyVo.setVipPriceList(vipPriceList);
			}
			return roomMoneyVo;
		}).collect(Collectors.toList());
		log.info("获取酒店房型列表--end,result:{}", JSON.toJSONString(roomMoneyVos));
		return roomMoneyVos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRoomNum(HotelRoomEntity hotelRoomEntity, HotelRoomMoneyEntity hotelRoomMoneyEntity, Long dateTime, int roomNum) {
		HotelRoomNumEntity hotelRoomNumEntity = hotelRoomNumDao.selectOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getDateday, dateTime).eq(HotelRoomNumEntity::getMoneyId, hotelRoomMoneyEntity.getId()));
		hotelRoomNumDao.updateRoomNum(hotelRoomNumEntity, roomNum);
	}

}