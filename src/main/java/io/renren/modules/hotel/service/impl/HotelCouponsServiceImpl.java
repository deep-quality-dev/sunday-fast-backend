package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelCouponsBreakfastDao;
import io.renren.modules.hotel.dao.HotelCouponsCashDao;
import io.renren.modules.hotel.dao.HotelCouponsDao;
import io.renren.modules.hotel.dao.HotelMemberCouponsDao;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.entity.HotelCouponsRoomsEntity;
import io.renren.modules.hotel.entity.HotelMemberCouponsEntity;
import io.renren.modules.hotel.service.HotelCouponsRoomsService;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.vo.UserCoupons;
import io.renren.modules.hotel.vo.WalletDataVo;

@Service("hotelCouponsService")
public class HotelCouponsServiceImpl extends ServiceImpl<HotelCouponsDao, HotelCouponsEntity> implements HotelCouponsService {

	@Autowired
	private HotelCouponsCashDao hotelCouponsCashDao;

	@Autowired
	private HotelCouponsBreakfastDao hotelCouponsBreakfastDao;

	@Autowired
	private HotelMemberCouponsDao hotelMemberCouponsDao;

	@Autowired
	private HotelCouponsRoomsService hotelCouponsRoomsService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelCouponsEntity> page = this.page(new Query<HotelCouponsEntity>().getPage(params), new QueryWrapper<HotelCouponsEntity>().eq(sellerId != null, "seller_id", sellerId));

		return new PageUtils(page);
	}

	@Override
	public PageUtils sellerCoupons(Long sellerId, Long userId, Map<String, Object> params) {
		QueryWrapper<HotelCouponsEntity> queryWrapper = new QueryWrapper<HotelCouponsEntity>();
		queryWrapper.eq("seller_id", sellerId);
		List<UserCoupons> userCoupons = new ArrayList<>();
		UserCoupons coupons = null;
		IPage<HotelCouponsEntity> page = this.page(new Query<HotelCouponsEntity>().getPage(params), queryWrapper);
		List<HotelCouponsEntity> couponsEntities = page.getRecords();
		for (HotelCouponsEntity hotelCouponsEntity : couponsEntities) {
			coupons = new UserCoupons();
			BeanUtil.copyProperties(hotelCouponsEntity, coupons);
			userCoupons.add(coupons);
		}
		return new PageUtils(userCoupons, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public Page<UserCoupons> userCoupons(Long userId, int status, Page<UserCoupons> page) {
		return baseMapper.userCoupons(page, status, userId);
	}

	@Override
	public WalletDataVo walletData(Long userId) {
		return baseMapper.walletData(userId);
	}

	@Override
	public Page<UserCoupons> userCashCoupons(Long userId, int status, Page<UserCoupons> page) {
		return hotelCouponsCashDao.userCashCouponsPage(page, status, userId);
	}

	@Override
	public Page<UserCoupons> userBreakfastCoupons(Long userId, int status, Page<UserCoupons> page) {
		return hotelCouponsBreakfastDao.userBreakfastCoupons(page, status, userId);
	}

	@Override
	public List<UserCoupons> canUseCoupons(Long userId, Long sellerId, BigDecimal amount) {
		return hotelCouponsCashDao.canUseCoupons(userId, sellerId, amount);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendCoupons(Long sellerId, List<Long> memberIds, List<Long> couponsIds, int couponsType) {
		HotelMemberCouponsEntity hotelMemberCouponsEntity = null;
		for (Long memberId : memberIds) {
			for (Long couponsId : couponsIds) {
				hotelMemberCouponsEntity = new HotelMemberCouponsEntity();
				hotelMemberCouponsEntity.setCouponsType(couponsType);
				hotelMemberCouponsEntity.setSellerId(sellerId);
				hotelMemberCouponsEntity.setTime(DateUtil.date().getTime());
				hotelMemberCouponsEntity.setState(1);
				hotelMemberCouponsEntity.setCouponsId(couponsId);
				hotelMemberCouponsEntity.setUserId(memberId);
				hotelMemberCouponsDao.insert(hotelMemberCouponsEntity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveCoupons(HotelCouponsEntity hotelCoupons) {
		baseMapper.insert(hotelCoupons);
		List<Long> roomsIds = hotelCoupons.getRoomIds();
		if (CollectionUtil.isNotEmpty(roomsIds)) {
			HotelCouponsRoomsEntity hotelCouponsRoomsEntity = null;
			for (Long roomId : roomsIds) {
				hotelCouponsRoomsEntity = new HotelCouponsRoomsEntity();
				hotelCouponsRoomsEntity.setCouponsId(hotelCoupons.getId());
				hotelCouponsRoomsEntity.setRoomId(roomId);
				hotelCouponsRoomsService.save(hotelCouponsRoomsEntity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCoupons(HotelCouponsEntity hotelCoupons) {
		baseMapper.updateById(hotelCoupons);
		// 先删除
		hotelCouponsRoomsService.remove(Wrappers.<HotelCouponsRoomsEntity>lambdaQuery().eq(HotelCouponsRoomsEntity::getCouponsId, hotelCoupons.getId()));
		List<Long> roomsIds = hotelCoupons.getRoomIds();
		if (CollectionUtil.isNotEmpty(roomsIds)) {
			HotelCouponsRoomsEntity hotelCouponsRoomsEntity = null;
			for (Long roomId : roomsIds) {
				hotelCouponsRoomsEntity = new HotelCouponsRoomsEntity();
				hotelCouponsRoomsEntity.setCouponsId(hotelCoupons.getId());
				hotelCouponsRoomsEntity.setRoomId(roomId);
				hotelCouponsRoomsService.save(hotelCouponsRoomsEntity);
			}
		}
	}

	@Override
	public List<UserCoupons> sellerCanUseBreakCoupons(Long userId, Long sellerId) {
		List<UserCoupons> coupons = hotelCouponsBreakfastDao.sellerCanUseBreakCoupons(userId, sellerId);
		return coupons;
	}

	@Override
	public List<UserCoupons> canUseFreeRoomCoupons(Long userId, Long sellerId, Long roomId) {
		List<UserCoupons> coupons = baseMapper.canUseFreeRoomCoupons(userId, sellerId, roomId);
		return coupons;
	}

}