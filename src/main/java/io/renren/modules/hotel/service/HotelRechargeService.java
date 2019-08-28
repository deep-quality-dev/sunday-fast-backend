package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelRechargeEntity;
import io.renren.modules.hotel.form.CardRechargeForm;

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
	 * @param userId
	 * @param cardRechargeForm
	 */
	void cardRecharge(Long userId, CardRechargeForm cardRechargeForm);
}
