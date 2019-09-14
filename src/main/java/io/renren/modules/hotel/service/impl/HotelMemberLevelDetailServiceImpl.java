package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelMemberLevelDetailDao;
import io.renren.modules.hotel.dto.HotelMemberLevelDetailDto;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;

@Service("hotelMemberLevelDetailService")
public class HotelMemberLevelDetailServiceImpl extends ServiceImpl<HotelMemberLevelDetailDao, HotelMemberLevelDetailEntity> implements HotelMemberLevelDetailService {

	@Autowired
	private HotelMemberLevelDao hotelMemberLevelDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		Object name = params.get("name");
		Object mobile = params.get("mobile");
		System.out.println(StrUtil.isNotEmpty(String.valueOf(mobile)));
		IPage<HotelMemberLevelDetailEntity> page = this.page(new Query<HotelMemberLevelDetailEntity>().getPage(params), new QueryWrapper<HotelMemberLevelDetailEntity>().eq(sellerId != null, "seller_id", sellerId).like(StrUtil.isNotEmpty(String.valueOf(name)), "name", name).like(StrUtil.isNotEmpty(String.valueOf(mobile)), "mobile", mobile));
		List<HotelMemberLevelDetailEntity> memberLevelDetailEntities = page.getRecords();
		List<HotelMemberLevelDetailDto> hotelMemberLevelDetailDtos = memberLevelDetailEntities.stream().map((HotelMemberLevelDetailEntity item) -> {
			HotelMemberLevelDetailDto hotelMemberLevelDetailDto = new HotelMemberLevelDetailDto();
			BeanUtil.copyProperties(item, hotelMemberLevelDetailDto);
			hotelMemberLevelDetailDto.setLevelName(hotelMemberLevelDao.selectById(item.getLevelId()).getName());
			return hotelMemberLevelDetailDto;
		}).collect(Collectors.toList());

		IPage<HotelMemberLevelDetailDto> resultPage = new Page<HotelMemberLevelDetailDto>();
		resultPage.setRecords(hotelMemberLevelDetailDtos);
		resultPage.setCurrent(page.getCurrent());
		resultPage.setPages(page.getPages());
		resultPage.setSize(page.getSize());
		resultPage.setTotal(page.getTotal());
		return new PageUtils(resultPage);
	}

	@Override
	public boolean hasVipCard(Long userId, Long sellerId) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = baseMapper.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, sellerId).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		return hotelMemberLevelDetailEntity != null;
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

}