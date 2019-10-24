package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import cn.hutool.core.util.StrUtil;
import io.renren.common.exception.RRException;
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
import io.renren.modules.hotel.form.SettingRoomStatusForm;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomNumService;
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
	private HotelRoomNumService hotelRoomNumService;

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
		List<HotelRoomEntity> hotelRoomEntities = this.list(Wrappers.<HotelRoomEntity>lambdaQuery().eq(HotelRoomEntity::getSellerId, sellerId).eq(HotelRoomEntity::getClassify, roomType).eq(HotelRoomEntity::getState, 1));
		List<RoomVO> roomVOs = hotelRoomEntities.stream().map((HotelRoomEntity item) -> {
			RoomVO roomVO = new RoomVO();
			BeanUtil.copyProperties(item, roomVO);
			if (StrUtil.isNotEmpty(item.getImg())) {
				roomVO.setImgs(Arrays.asList(item.getImg().split(",")));
			}
			roomVO.setPrice(NumberUtil.decimalFormat("0.00", item.getPrice().doubleValue()));
			// 获取房价列表
			List<RoomMoneyVo> roomMoneyVos = this.roomMoneys(memberLevelEntity, hotelMemberLevelEntities, item.getId(), DateUtil.parse(startTime), DateUtil.parse(endTime));
			roomVO.setAmountItems(roomMoneyVos);
			int nums = roomMoneyVos.stream().mapToInt(RoomMoneyVo::getHasRoom).sum();
			// 日期区间是否有满房情况
			int result = hotelRoomNumDao.selectCount(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getRid, item.getId()).between(HotelRoomNumEntity::getDateday, DateUtil.parse(startTime).getTime(), DateUtil.parse(endTime).getTime()).lt(HotelRoomNumEntity::getNums, 1));
			roomVO.setHasRoom(result == 0 && nums > 0);
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void show(Long sellerId, Long id) {
		HotelRoomEntity hotelRoomEntity = baseMapper.selectById(id);
		if (hotelRoomEntity.getSellerId().intValue() != sellerId.intValue()) {
			throw new RRException("启用失败");
		}
		hotelRoomEntity.setState(1);
		baseMapper.updateById(hotelRoomEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void hide(Long sellerId, Long id) {
		HotelRoomEntity hotelRoomEntity = baseMapper.selectById(id);
		if (hotelRoomEntity.getSellerId().intValue() != sellerId.intValue()) {
			throw new RRException("禁用失败");
		}
		hotelRoomEntity.setState(0);
		baseMapper.updateById(hotelRoomEntity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void settingRoomStatusBatch(Long sellerId, SettingRoomStatusForm settingRoomStatusForms) {
		List<String> dates = settingRoomStatusForms.getDates();
		List<Long> moneyIds = settingRoomStatusForms.getMoneyIds();
		List<Long> roomIds = settingRoomStatusForms.getRoomIds();

		// 房型状态设置
		if (settingRoomStatusForms.getStatus() >= -1 && settingRoomStatusForms.getStatus() < 2) {
			if (settingRoomStatusForms.getStatus() != -1) {
				baseMapper.updateRoomStatusBatch(roomIds, settingRoomStatusForms.getStatus());
			}
		} else {
			throw new RRException("房价状态参数错误");
		}
		List<String> dateList = this.findDates(dates.get(0), dates.get(1));
		// 房价数量设置
		if (settingRoomStatusForms.getNumType() != 0) {
			for (Long moenyId : moneyIds) {
				HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(moenyId);
				for (String day : dateList) {
					HotelRoomNumEntity hotelRoomNumEntity = hotelRoomNumDao.selectOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getMoneyId, moenyId).eq(HotelRoomNumEntity::getDateday, DateUtil.parse(day).getTime()));
					if (null == hotelRoomNumEntity) {
						hotelRoomNumEntity = new HotelRoomNumEntity();
						hotelRoomNumEntity.setDateday(DateUtil.parse(day).getTime());
						hotelRoomNumEntity.setMoneyId(moenyId);
						hotelRoomNumEntity.setRid(hotelRoomMoneyEntity.getRoomId());
						hotelRoomNumEntity.setNums(hotelRoomMoneyEntity.getNum());
					}
					// 增加
					if (settingRoomStatusForms.getNumType() == 1) {
						hotelRoomNumEntity.setNums(hotelRoomNumEntity.getNums() + settingRoomStatusForms.getNum());
					}
					// 减少
					if (settingRoomStatusForms.getNumType() == 2) {
						hotelRoomNumEntity.setNums(hotelRoomNumEntity.getNums() - settingRoomStatusForms.getNum());
						if (hotelRoomNumEntity.getNums() < 0) {
							throw new RRException(hotelRoomMoneyEntity.getName() + "【" + day + "】数量小于0");
						}
					}
					// 设定
					if (settingRoomStatusForms.getNumType() == 3) {
						hotelRoomNumEntity.setNums(settingRoomStatusForms.getNum());
					}
					hotelRoomNumService.saveOrUpdate(hotelRoomNumEntity);
				}
			}
		}
	}

	// 区间日期
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

}