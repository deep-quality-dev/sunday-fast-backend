package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.NumberUtil;
import io.renren.common.exception.RRException;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelMemberLevelDetailDao;
import io.renren.modules.hotel.dto.HotelSellerMemberDto;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;

@Service("hotelMemberLevelDetailService")
public class HotelMemberLevelDetailServiceImpl extends ServiceImpl<HotelMemberLevelDetailDao, HotelMemberLevelDetailEntity> implements HotelMemberLevelDetailService {

	@Autowired
	private HotelMemberLevelDao hotelMemberLevelDao;

	@Override
	public IPage<HotelSellerMemberDto> queryPage(Map<String, Object> params) {
		int pageNum = Integer.valueOf(params.get("page").toString());
		int limit = Integer.valueOf(params.get("limit").toString());
		Page<HotelSellerMemberDto> page = new Page<HotelSellerMemberDto>(pageNum, limit);
		IPage<HotelSellerMemberDto> result = hotelMemberLevelDao.pageList(page, params);
		return result;
	}

	@Override
	public HotelMemberLevelDetailEntity hasVipCard(Long userId, Long sellerId) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = baseMapper.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		return hotelMemberLevelDetailEntity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void balanceTransaction(Long sellerId, Long userId, BigDecimal totalCost) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = baseMapper.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		if (null != hotelMemberLevelDetailEntity) {
			if (NumberUtil.isGreater(totalCost, hotelMemberLevelDetailEntity.getBalance())) {
				throw new RRException("余额不足");
			}
			// 扣除余额
			baseMapper.updateBanlance(hotelMemberLevelDetailEntity, totalCost);
			return;
		}
		throw new RRException("余额不足");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void integralTransaction(Long sellerId, Long userId, BigDecimal totalCost) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = baseMapper.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		if (null != hotelMemberLevelDetailEntity) {
			if (NumberUtil.isGreater(totalCost, hotelMemberLevelDetailEntity.getScore())) {
				throw new RRException("积分不足");
			}
			// 扣除余额
			baseMapper.updateintegral(hotelMemberLevelDetailEntity, totalCost);
			return;
		}
		throw new RRException("积分不足");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addIntegral(Long sellerId, Long userId, BigDecimal totalCost) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = baseMapper.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		baseMapper.addIntegral(hotelMemberLevelDetailEntity, totalCost);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addBalance(Long sellerId, Long userId, BigDecimal totalCost) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = baseMapper.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		baseMapper.addBalance(hotelMemberLevelDetailEntity, totalCost);
	}

}