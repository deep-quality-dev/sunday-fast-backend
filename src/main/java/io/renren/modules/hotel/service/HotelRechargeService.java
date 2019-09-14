package io.renren.modules.hotel.service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRechargeEntity;
import io.renren.modules.hotel.form.CardRechargeForm;
import io.renren.modules.hotel.vo.CardConsumptionVo;

/**
 * 充值表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
public interface HotelRechargeService extends IService<HotelRechargeEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 卡片充值
	 * 
	 * @param userId
	 * @param cardRechargeForm
	 */
	WxPayMpOrderResult cardRecharge(Long userId, CardRechargeForm cardRechargeForm);

	/**
	 * 卡片消费记录
	 * 
	 * @param page
	 * @param userId
	 * @param cardId
	 * @return
	 */
	Page<CardConsumptionVo> consumptionRecord(Page<CardConsumptionVo> page, Long userId, Long cardId);

	/**
	 * 充值回调
	 * 
	 * @param outTradeNo
	 * @param formId
	 */
	void cardRechargeHandler(String outTradeNo, String formId);

	/**
	 * 添加消费记录
	 * 
	 * @param consumptionRecordEntity
	 */
	void addConsumptionRecord(BigDecimal amount, Long cardId, Long userId, String note);
}
