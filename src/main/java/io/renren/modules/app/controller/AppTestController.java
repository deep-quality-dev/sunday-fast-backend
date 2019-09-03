/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.swagger.annotations.Api;

/**
 * 测试接口
 *
 */
@RestController
@RequestMapping("/app")
@Api("APP测试接口")
public class AppTestController {


	@Autowired
	private HotelSellerService hotelSellerService;

	@GetMapping("/improt")
	public R improt() {
		hotelSellerService.test();
		return R.ok();
	}

//	@Login
//	@GetMapping("userInfo")
//	@ApiOperation("获取用户信息")
//	public R userInfo(@LoginUser UserEntity user) {
//		return R.ok().put("user", user);
//	}
//
//	@Login
//	@GetMapping("userId")
//	@ApiOperation("获取用户ID")
//	public R userInfo(@RequestAttribute("userId") Integer userId) {
//		return R.ok().put("userId", userId);
//	}
//
//	@GetMapping("notToken")
//	@ApiOperation("忽略Token验证测试")
//	public R notToken() {
//		return R.ok().put("msg", "无需token也能访问。。。");
//	}

}
