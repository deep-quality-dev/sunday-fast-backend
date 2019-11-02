package io.renren.modules.hotel.service;

import java.math.BigDecimal;
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
	 * @param userId 用户ID·
	 * @param params
	 * @return
	 */
	Page<UserCoupons> userCoupons(Long userId, int status, Page<UserCoupons> page);

	/**
	 * 钱包主页数据
	 * 
	 * @param userId
	 * @return
	 */
	WalletDataVo walletData(Long userId);

	/**
	 * 用户代金券
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @return
	 */
	Page<UserCoupons> userCashCoupons(Long userId, int status, Page<UserCoupons> page);

	/**
	 * 用户早餐券
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @return
	 */
	Page<UserCoupons> userBreakfastCoupons(Long userId, int status, Page<UserCoupons> page);

	/**
	 * 商家可用优惠券
	 * 
	 * @param userId
	 * @param sellerId
	 * @param amount
	 * @return
	 */
	List<UserCoupons> canUseCoupons(Long userId, Long sellerId, BigDecimal amount);

	/**
	 * 发放优惠券
	 * 
	 * @param sellerId
	 * @param memberIds
	 * @param couponsIds
	 * @param type
	 */
	void sendCoupons(Long sellerId, List<Long> memberIds, List<Long> couponsIds, int type);

	/**
	 * 保存免房券
	 * 
	 * @param hotelCoupons
	 */
	void saveCoupons(HotelCouponsEntity hotelCoupons);

	/**
	 * 修改免房券
	 * 
	 * @param hotelCoupons
	 */
	void updateCoupons(HotelCouponsEntity hotelCoupons);

	/**
	 * 商家可用早餐券
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	List<UserCoupons> sellerCanUseBreakCoupons(Long userId, Long sellerId);

	/**
	 * 可用免房券
	 * @param userId
	 * @param sellerId
	 * @param roomId
	 * @return
	 */
	List<UserCoupons> canUseFreeRoomCoupons(Long userId, Long sellerId, Long roomId);

}
