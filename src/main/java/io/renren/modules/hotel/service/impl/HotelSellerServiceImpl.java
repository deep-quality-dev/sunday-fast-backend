package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelAssessDao;
import io.renren.modules.hotel.dao.HotelMemberCollectDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.entity.HotelMemberCollectEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.map.GaodeAPI;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.HotelInfo;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import io.renren.modules.hotel.vo.HotelSearchVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelSellerService")
public class HotelSellerServiceImpl extends ServiceImpl<HotelSellerDao, HotelSellerEntity> implements HotelSellerService {

	@Autowired
	private HotelMemberCollectDao hotelMemberCollectDao;

	@Autowired
	private HotelAssessDao hotelAssessDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object state = params.get("state");
		IPage<HotelSellerEntity> page = this.page(new Query<HotelSellerEntity>().getPage(params), new QueryWrapper<HotelSellerEntity>().eq("state", state));

		return new PageUtils(page);
	}

	@Override
	public HotelInfo sellerInfo(Long userId, Long sellerId) {
		log.info("获取酒店信息--start,sellerId", sellerId);
		HotelInfo hotelInfo = new HotelInfo();
		HotelSellerEntity hotelSellerEntity = this.getById(sellerId);
		BeanUtil.copyProperties(hotelSellerEntity, hotelInfo);
		hotelInfo.setImg(hotelSellerEntity.getImg());
		HotelMemberCollectEntity hotelMemberCollectEntity = hotelMemberCollectDao.selectOne(Wrappers.<HotelMemberCollectEntity>lambdaQuery().eq(HotelMemberCollectEntity::getBizId, sellerId).eq(HotelMemberCollectEntity::getBizType, 1).eq(HotelMemberCollectEntity::getUserId, userId));
		if (null != hotelMemberCollectEntity) {
			hotelInfo.setCollect(1);
		}
		int commentCount = hotelAssessDao.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().eq(HotelAssessEntity::getSellerId, sellerId));
		hotelInfo.setCommentCount(commentCount);
		// 好评
		int goodsCommentCount = hotelAssessDao.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().eq(HotelAssessEntity::getSellerId, sellerId).ge(HotelAssessEntity::getScore, 3));
		if (commentCount > 0) {
			hotelInfo.setCommentRate(NumberUtil.decimalFormat("#.##%", NumberUtil.div(goodsCommentCount, commentCount)));
		}
		// 平均评分
		double score = hotelAssessDao.avgScore(sellerId);
		hotelInfo.setScore(NumberUtil.round(score, 2));
		log.info("获取酒店信息--end,result:{}", JSON.toJSONString(hotelInfo));
		return hotelInfo;
	}

	@Override
	public Page<HotelItemVo> hotelPage(Long userId, HotelSearchCondition params, Page<HotelItemVo> page) {
		Page<HotelItemVo> pageResult = baseMapper.hotelPage(page, params);
		List<HotelItemVo> hotelItemVos = pageResult.getRecords();
		for (HotelItemVo hotelItemVo : hotelItemVos) {
			hotelItemVo.setKm(GaodeAPI.getDistance(hotelItemVo.getLonLat(), params.getLonLat()));
			double score = hotelAssessDao.avgScore(hotelItemVo.getId());
			hotelItemVo.setScore(NumberUtil.round(score, 2));
		}
		pageResult.setRecords(hotelItemVos);
		return pageResult;
	}

	@Override
	public Page<HotelSearchVo> search(String kw, Page<HotelSearchVo> page) {
		Page<HotelSearchVo> pageResult = baseMapper.search(page, kw);
		return pageResult;
	}

}