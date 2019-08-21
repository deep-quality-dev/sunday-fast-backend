package io.renren.modules.hotel.service;

import io.renren.modules.hotel.vo.WalletDataVo;

/**
 * 钱包接口
 * 
 * @author taoz
 *
 */
public interface HotelWalletService {

	/**
	 * 钱吧首页数据
	 * 
	 * @param userId
	 * @return
	 */
	WalletDataVo walletData(Long userId);

}
