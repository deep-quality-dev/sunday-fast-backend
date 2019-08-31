/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.renren.common.utils.Constant;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.sys.entity.SysUserEntity;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class AbstractController {

	@Autowired
	private HotelSellerService hotelSellerService;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected boolean isAdmin() {
		return getUserId() == Constant.SUPER_ADMIN;
	}

	protected Long getSellerId() {
		HotelSellerEntity hotelSeller = hotelSellerService.getOne(new QueryWrapper<HotelSellerEntity>().eq("user_id", getUserId()).eq("state", 2));
		return hotelSeller.getId();
	}
}
