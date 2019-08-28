package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
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
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.form.BecomeVipForm;
import io.renren.modules.hotel.form.BindVipCardForm;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.hotel.service.HotelMemberLevelService;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.VipCardInfoVo;
import io.renren.modules.hotel.vo.VipCardItemVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelMemberLevelService")
public class HotelMemberLevelServiceImpl extends ServiceImpl<HotelMemberLevelDao, HotelMemberLevelEntity> implements HotelMemberLevelService {

	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	@Autowired
	private HotelMemberLevelService hotelMemberLevelService;

	@Autowired
	private HotelSellerService hotelSellerService;

	@Autowired
	private HotelMemberService hotelMemberService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelMemberLevelEntity> page = this.page(new Query<HotelMemberLevelEntity>().getPage(params), new QueryWrapper<HotelMemberLevelEntity>());

		return new PageUtils(page);
	}

	@Override
	public void bindCard(Long userId, BindVipCardForm vipCardForm) {
		// TODO 查询酒店系统是否有会员

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void becomeVip(Long userId, BecomeVipForm becomeVipForm) {
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelMemberLevelEntity hotelMemberLevelEntity = hotelMemberLevelService.getById(becomeVipForm.getLevelId());
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, hotelMemberLevelEntity.getId()));
		if (null != hotelMemberLevelDetailEntity) {
			log.error("重复办理会员卡，userId:{},parms:{}", userId, JSON.toJSONString(becomeVipForm));
			throw new RRException("请勿重复办理");
		}
		hotelMemberLevelDetailEntity = new HotelMemberLevelDetailEntity();
		BeanUtil.copyProperties(becomeVipForm, hotelMemberLevelDetailEntity);
		hotelMemberLevelDetailEntity.setMemberId(userId);
		hotelMemberLevelDetailEntity.setStatus(1);
//		if (1 == hotelMemberLevelEntity.getPayFlag()) {
//			hotelMemberLevelDetailEntity.setStatus(-1);
//		}
		hotelMemberLevelDetailEntity.setSellerId(hotelMemberLevelEntity.getSellerId());
		hotelMemberLevelDetailEntity.setMobile(hotelMemberEntity.getTel());
		hotelMemberLevelDetailService.save(hotelMemberLevelDetailEntity);
	}

	@Override
	public int checkVipStatus(Long userId, Long sellerId) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		return hotelMemberLevelDetailEntity == null ? -1 : 1;
	}

	@Override
	public VipCardInfoVo vipCardInfo(Long userId, Long sellerId) {
		VipCardInfoVo cardInfoVo = new VipCardInfoVo();
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		HotelMemberLevelEntity hotelMemberLevelEntity = baseMapper.selectById(hotelMemberLevelDetailEntity.getLevelId());
		cardInfoVo.setCertificateNo(hotelMemberLevelDetailEntity.getCertificateNo());
		cardInfoVo.setMobile(hotelMemberLevelDetailEntity.getMobile());
		cardInfoVo.setIcon(hotelMemberLevelEntity.getIcon());
		cardInfoVo.setName(hotelMemberLevelEntity.getName());
		return cardInfoVo;
	}

	@Override
	public List<VipCardItemVo> vipCardList(Long userId, Long sellerId) {
		List<VipCardItemVo> cardItemVos = new ArrayList<VipCardItemVo>();
		List<HotelMemberLevelEntity> hotelMemberLevelEntities = baseMapper.selectList(Wrappers.<HotelMemberLevelEntity>lambdaQuery().eq(HotelMemberLevelEntity::getSellerId, sellerId));
		cardItemVos = hotelMemberLevelEntities.stream().map((HotelMemberLevelEntity item) -> {
			VipCardItemVo cardItemVo = new VipCardItemVo();
			BeanUtil.copyProperties(item, cardItemVo);
			cardItemVo.setSellerName(hotelSellerService.getById(item.getSellerId()).getName());
			return cardItemVo;
		}).collect(Collectors.toList());
		return cardItemVos;
	}

	@Override
	public List<VipCardItemVo> userCardlist(Long userId) {
		List<VipCardItemVo> cardItemVos = new ArrayList<VipCardItemVo>();
		List<HotelMemberLevelEntity> hotelMemberLevelEntities = baseMapper.userCardList(userId);
		cardItemVos = hotelMemberLevelEntities.stream().map((HotelMemberLevelEntity item) -> {
			VipCardItemVo cardItemVo = new VipCardItemVo();
			cardItemVo.setSellerName(hotelSellerService.getById(item.getSellerId()).getName());
			BeanUtil.copyProperties(item, cardItemVo);
			return cardItemVo;
		}).collect(Collectors.toList());
		return cardItemVos;
	}

}