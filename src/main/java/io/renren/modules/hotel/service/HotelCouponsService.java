package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.vo.WalletDataVo;

/**
 * 优惠券
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
public interface HotelCouponsService extends IService<HotelCouponsEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 商家优惠券
	 * 
	 * @param sellerId 商家ID
	 * @param userId   用户ID·
	 * @param params
	 * @return
	 */
	PageUtils sellerCoupons(Long sellerId, Long userId, Map<String, Object> params);

	/**
	 * 用户优惠券
	 * 
	 * @param sellerId 商家ID
	 * @param userId   用户ID·
	 * @param params
	 * @return
	 */
	PageUtils userCoupons(Long sellerId, Long userId, Map<String, Object> params);

	/**
	 * 	钱包主页数据
	 * @param userId
	 * @return
	 */
	WalletDataVo walletData(Long userId);
	
}
