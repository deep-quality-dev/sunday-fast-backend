package io.renren.modules.hotel.controller.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.service.HotelWxConfigService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {

	@Autowired
	private HotelWxConfigService hotelWxConfigService;

	/**
	 * 商家ID
	 */
	/**
	 * 根据app ID 获取商家信息
	 * 
	 * @param appId
	 * @return
	 */
	protected Long sellerId(String appId) {
		log.info("获取商家ID--start，appId:{}", appId);
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>().eq("app_id", appId).eq("enabled", 1));
		log.info("获取商家ID--end，result:{}", JSON.toJSONString(hotelWxConfigEntity));
		return hotelWxConfigEntity.getSellerId();
	}

}
