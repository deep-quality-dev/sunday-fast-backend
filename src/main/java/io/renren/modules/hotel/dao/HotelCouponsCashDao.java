package io.renren.modules.hotel.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.hotel.entity.HotelCouponsCashEntity;
import io.renren.modules.hotel.vo.UserCoupons;

/**
 * 代金券
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 22:22:26
 */
@Mapper
public interface HotelCouponsCashDao extends BaseMapper<HotelCouponsCashEntity> {

	/**
	 * 用户代金券
	 * 
	 * @param page
	 * @param userId
	 * @return
	 */
	Page<UserCoupons> userCashCouponsPage(Page<UserCoupons> page,int status, Long userId);

	/**
	 * 商家可用优惠券
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	List<UserCoupons> canUseCoupons(Long userId, Long sellerId, BigDecimal amount);

}
