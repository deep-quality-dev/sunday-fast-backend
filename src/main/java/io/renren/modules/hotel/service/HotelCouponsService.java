package io.renren.modules.hotel.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.vo.UserCoupons;
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
	 * @param userId   用户ID·
	 * @param params
	 * @return
	 */
	Page<UserCoupons> userCoupons(Long userId, Page<UserCoupons> page);

	/**
	 * 	钱包主页数据
	 * @param userId
	 * @return
	 */
	WalletDataVo walletData(Long userId);

	/**
	 * 用户代金券
	 * @param userId
	 * @param page
	 * @param limit
	 * @return
	 */
	Page<UserCoupons> userCashCoupons(Long userId, Page<UserCoupons> page);

	/**
	 * 用户早餐券
	 * @param userId
	 * @param page
	 * @param limit
	 * @return
	 */
	Page<UserCoupons> userBreakfastCoupons(Long userId, Page<UserCoupons> page);

	/**
	 * 商家可用优惠券
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	List<UserCoupons> canUseCoupons(Long userId, Long sellerId);
	
}
