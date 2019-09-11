/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import io.renren.common.utils.R;
import io.renren.modules.constants.HotelWxMsgTemplate;
import io.renren.modules.hotel.config.WxMaConfiguration;
import io.renren.modules.hotel.config.WxMpConfiguration;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.entity.HotelWxTemplateEntity;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelWxTemplateService;
import io.swagger.annotations.Api;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

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

	@GetMapping("/testTpl")
	public void testTpl() throws WxErrorException {
		List<WxMaTemplateData> maTemplateDatas = new ArrayList<WxMaTemplateData>();
		maTemplateDatas.add(new WxMaTemplateData("first", "您好，您的订单已取消"));
		maTemplateDatas.add(new WxMaTemplateData("keyword1", "keyword1"));
		maTemplateDatas.add(new WxMaTemplateData("keyword2", "keyword2"));
		maTemplateDatas.add(new WxMaTemplateData("keyword3", "keyword3"));
		maTemplateDatas.add(new WxMaTemplateData("keyword4", "100"));
		maTemplateDatas.add(new WxMaTemplateData("keyword5", "11111"));
		maTemplateDatas.add(new WxMaTemplateData("keyword6", "66666"));
		maTemplateDatas.add(new WxMaTemplateData("remark", "您的订单已取消，期待你的下次预定。"));
		WxMaTemplateMessage maTemplateMessage = new WxMaTemplateMessage("odHq-4ru1nfp6YcKVJcHhEkjAEtk", "qFLAITJmXZ37LFyaQMmk3XF88nQATfUW-RUNdUD8RTU", null, "wx11221358462029f8e5defd641281527300", maTemplateDatas, null);
		WxMaConfiguration.getMaService("wx2fc4acedc3bc2391").getMsgService().sendTemplateMsg(maTemplateMessage);

	}

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
