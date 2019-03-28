package io.renren.modules.hotel.service;

import java.util.Map;

/**
 * 交易
 * @author taoz
 *
 */
public interface TransactionService {

	/**
	 * 退款
	 * @param refundParams
	 */
	void refund(Map<String,String> refundParams);
}
