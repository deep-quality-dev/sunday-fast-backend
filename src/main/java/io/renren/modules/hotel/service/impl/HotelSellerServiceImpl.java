package io.renren.modules.hotel.service.impl;

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
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelMemberCollectDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
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

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelSellerEntity> page = this.page(new Query<HotelSellerEntity>().getPage(params), new QueryWrapper<HotelSellerEntity>());

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
		log.info("获取酒店信息--end,result:{}", JSON.toJSONString(hotelInfo));
		return hotelInfo;
	}

	@Override
	public Page<HotelItemVo> hotelPage(Long userId, HotelSearchCondition params, Page<HotelItemVo> page) {
		Page<HotelItemVo> pageResult = baseMapper.hotelPage(page, params);
		List<HotelItemVo> hotelItemVos = pageResult.getRecords();
		for (HotelItemVo hotelItemVo : hotelItemVos) {
			hotelItemVo.setKm(GaodeAPI.getDistance(hotelItemVo.getLonLat(), params.getLonLat()));
			hotelItemVo.setScore(5.5);
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