package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelCouponsBreakfastDao;
import io.renren.modules.hotel.dao.HotelCouponsCashDao;
import io.renren.modules.hotel.dao.HotelCouponsDao;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.vo.UserCoupons;
import io.renren.modules.hotel.vo.WalletDataVo;

@Service("hotelCouponsService")
public class HotelCouponsServiceImpl extends ServiceImpl<HotelCouponsDao, HotelCouponsEntity> implements HotelCouponsService {

	@Autowired
	private HotelCouponsCashDao hotelCouponsCashDao;

	@Autowired
	private HotelCouponsBreakfastDao hotelCouponsBreakfastDao;

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
		return baseMapper.userCoupons(page,status, userId);
	}

	@Override
	public WalletDataVo walletData(Long userId) {
		return baseMapper.walletData(userId);
	}

	@Override
	public Page<UserCoupons> userCashCoupons(Long userId, int status, Page<UserCoupons> page) {
		return hotelCouponsCashDao.userCashCouponsPage(page, status,userId);
	}

	@Override
	public Page<UserCoupons> userBreakfastCoupons(Long userId, int status, Page<UserCoupons> page) {
		return hotelCouponsBreakfastDao.userBreakfastCoupons(page, status, userId);
	}

	@Override
	public List<UserCoupons> canUseCoupons(Long userId, Long sellerId, BigDecimal amount) {
		return hotelCouponsCashDao.canUseCoupons(userId, sellerId, amount);
	}

}