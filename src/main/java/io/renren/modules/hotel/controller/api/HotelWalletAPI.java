package io.renren.modules.hotel.controller.api;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;

import io.renren.common.utils.NetworkUtil;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.entity.HotelRechargeConfigEntity;
import io.renren.modules.hotel.form.CardRechargeForm;
import io.renren.modules.hotel.service.HotelRechargeConfigService;
import io.renren.modules.hotel.service.HotelRechargeService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.service.HotelWalletService;
import io.renren.modules.hotel.vo.CardConsumptionVo;
import io.renren.modules.hotel.vo.HotelScore;
import io.renren.modules.hotel.vo.WalletDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;

@Api(value = "钱包", tags = { "钱包接口" })
@RestController
@RequestMapping("/hotel/wallet")
public class HotelWalletAPI {

	@Autowired
	private HotelWalletService hotelWalletService;

	@Autowired
	private HotelScoreService hotelScoreService;

	@Autowired
	private HotelRechargeConfigService hotelRechargeConfigService;

	@Autowired
	private HotelRechargeService hotelRechargeService;

	@Login
	@ApiOperation("钱包数据")
	@GetMapping("/walletData")
	public R walletData(@RequestAttribute("userId") Long userId) {
		WalletDataVo walletDataVo = hotelWalletService.walletData(userId);
		return R.ok(walletDataVo);
	}

	@Login
	@ApiOperation("用户卡片积分列表")
	@GetMapping("/scoreList")
	public R scoreList(@RequestAttribute("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit, Long cardId) {
		Page<HotelScore> pageResult = hotelScoreService.scoreList(userId, cardId, new Page<HotelScore>(page, limit));
		return R.ok(pageResult);
	}

	/**
	 * 卡片总积分
	 * 
	 * @param userId
	 * @param cardId
	 * @return
	 */
	@Login
	@ApiOperation("用户卡片总积分")
	@GetMapping("/scoreCount")
	public R scoreCount(@RequestAttribute("userId") Long userId, Long cardId) {
		BigDecimal scoreCount = hotelScoreService.scoreCount(userId, cardId);
		return R.ok().put("data", scoreCount);
	}

	@Login
	@ApiOperation("用户卡片消费记录")
	@GetMapping("/consumptionRecord")
	public R consumptionRecord(@RequestAttribute("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "limit", required = false, defaultValue = "10") int limit, Long cardId) {
		Page<CardConsumptionVo> pageRessult = hotelRechargeService.consumptionRecord(new Page<CardConsumptionVo>(page, limit), userId, cardId);
		return R.ok(pageRessult);
	}

	@Login
	@ApiOperation("获取充值列表")
	@GetMapping("/rechargeConfig")
	public R rechargeConfig(@RequestAttribute("userId") Long userId, Long cardId) {
		List<HotelRechargeConfigEntity> hotelRechargeConfigEntities = hotelRechargeConfigService.rechargeConfigList(userId, cardId);
		return R.ok(hotelRechargeConfigEntities);
	}

	@Login
	@SneakyThrows
	@ApiOperation("卡片充值")
	@PostMapping("/cardRecharge")
	public R cardRecharge(HttpServletRequest request, @RequestAttribute("userId") Long userId, @RequestBody CardRechargeForm cardRechargeForm) {
		cardRechargeForm.setIp(NetworkUtil.getIpAddress(request));
		WxPayMpOrderResult mpOrderResult = hotelRechargeService.cardRecharge(userId, cardRechargeForm);
		return R.ok(mpOrderResult);
	}

}
