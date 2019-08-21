package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelCouponsDao;
import io.renren.modules.hotel.dao.HotelMemberCouponsDao;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.entity.HotelMemberCouponsEntity;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.vo.UserCoupons;
import io.renren.modules.hotel.vo.WalletDataVo;

@Service("hotelCouponsService")
public class HotelCouponsServiceImpl extends ServiceImpl<HotelCouponsDao, HotelCouponsEntity> implements HotelCouponsService {

	@Autowired
	private HotelMemberCouponsDao hotelMemberCouponsDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelCouponsEntity> page = this.page(new Query<HotelCouponsEntity>().getPage(params), new QueryWrapper<HotelCouponsEntity>());

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
	public PageUtils userCoupons(Long sellerId, Long userId, Map<String, Object> params) {
		QueryWrapper<HotelMemberCouponsEntity> queryWrapper = new QueryWrapper<HotelMemberCouponsEntity>();
		queryWrapper.eq("seller_id", sellerId);
		queryWrapper.eq("user_id", userId);
		List<UserCoupons> userCoupons = new ArrayList<>();
		UserCoupons coupons = null;
		IPage<HotelMemberCouponsEntity> page = hotelMemberCouponsDao.selectPage(new Query<HotelMemberCouponsEntity>().getPage(params), queryWrapper);
		List<HotelMemberCouponsEntity> memberCouponsEntities = page.getRecords();
		HotelCouponsEntity hotelCouponsEntity = null;
		for (HotelMemberCouponsEntity memberCouponsEntity : memberCouponsEntities) {
			hotelCouponsEntity = this.getById(memberCouponsEntity.getCouponsId());
			coupons = new UserCoupons();
			BeanUtil.copyProperties(hotelCouponsEntity, coupons);
			userCoupons.add(coupons);
		}
		return new PageUtils(userCoupons, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public WalletDataVo walletData(Long userId) {
		return baseMapper.walletData(userId);
	}

}