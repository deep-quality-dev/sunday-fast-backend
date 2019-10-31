package io.renren.modules.hotel.service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelWithdrawalEntity;
import io.renren.modules.hotel.form.WithdrawalApplyForm;

/**
 * 提现记录
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
public interface HotelWithdrawalService extends IService<HotelWithdrawalEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 商家可提现金额
	 * 
	 * @param sellerId
	 * @return
	 */
	Map<String, Object> withdrawalApplyData(Long sellerId);

	/**
	 * 提交提现申请
	 * 
	 * @param sellerId
	 * @param withdrawalApplyForm
	 */
	void withdrawalApply(Long sellerId, WithdrawalApplyForm withdrawalApplyForm);

	/**
	 * 提现审核
	 * 
	 * @param id
	 */
	void withdrawalApplyAudit(Long id);
}
