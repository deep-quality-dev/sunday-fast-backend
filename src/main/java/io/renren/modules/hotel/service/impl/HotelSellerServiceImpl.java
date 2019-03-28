package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.HotelInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelSellerService")
public class HotelSellerServiceImpl extends ServiceImpl<HotelSellerDao, HotelSellerEntity> implements HotelSellerService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelSellerEntity> page = this.page(new Query<HotelSellerEntity>().getPage(params), new QueryWrapper<HotelSellerEntity>());

		return new PageUtils(page);
	}

	@Override
	public HotelInfo sellerInfo(Long sellerId) {
		log.info("获取酒店信息--start,sellerId", sellerId);
		HotelInfo hotelInfo = new HotelInfo();
		HotelSellerEntity hotelSellerEntity = this.getById(sellerId);
		BeanUtil.copyProperties(hotelSellerEntity, hotelInfo);
		log.info("获取酒店信息--end,result:{}", JSON.toJSONString(hotelInfo));
		return hotelInfo;
	}

}