package io.renren.modules.hotel.service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.dto.HotelSellerMemberDto;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;

/**
 * 会员卡详情
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-17 19:46:29
 */
public interface HotelMemberLevelDetailService extends IService<HotelMemberLevelDetailEntity> {

	IPage<HotelSellerMemberDto> queryPage(Map<String, Object> params);

	/**
	 * 用户是否有会员卡
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	HotelMemberLevelDetailEntity hasVipCard(Long userId, Long sellerId);

	/**
	 * 余额支付
	 * 
	 * @param sellerId
	 * @param userId
	 * @param totalCost
	 */
	void balanceTransaction(Long sellerId, Long userId, BigDecimal totalCost);

	/**
	 * 积分支付
	 * 
	 * @param sellerId
	 * @param userId
	 * @param totalCost
	 */
	void integralTransaction(Long sellerId, Long userId, BigDecimal totalCost);

	/**
	 * 添加积分
	 * 
	 * @param sellerId
	 * @param userId
	 * @param totalCost
	 */
	void addIntegral(Long sellerId, Long userId, BigDecimal totalCost);

	/**
	 * 添加余额
	 * 
	 * @param sellerId
	 * @param userId
	 * @param totalCost
	 */
	void addBalance(Long sellerId, Long userId, BigDecimal totalCost);
}
