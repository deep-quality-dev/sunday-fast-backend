package io.renren.modules.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelRechargeConfigDao;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.entity.HotelRechargeConfigEntity;
import io.renren.modules.hotel.service.HotelRechargeConfigService;

@Service("hotelRechargeConfigService")
public class HotelRechargeConfigServiceImpl extends ServiceImpl<HotelRechargeConfigDao, HotelRechargeConfigEntity> implements HotelRechargeConfigService {

	@Autowired
	private HotelMemberLevelDao hotelMemberLevelDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelRechargeConfigEntity> page = this.page(new Query<HotelRechargeConfigEntity>().getPage(params), new QueryWrapper<HotelRechargeConfigEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<HotelRechargeConfigEntity> rechargeConfigList(Long userId, Long cardId) {
		HotelMemberLevelEntity hotelMemberLevelEntity = hotelMemberLevelDao.selectById(cardId);
		return baseMapper.selectList(Wrappers.<HotelRechargeConfigEntity>lambdaQuery().eq(HotelRechargeConfigEntity::getSellerId, hotelMemberLevelEntity.getSellerId()));
	}

}