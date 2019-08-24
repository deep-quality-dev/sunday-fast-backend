package io.renren.modules.hotel.dao;

import io.renren.modules.hotel.entity.HotelCouponsCashEntity;
import io.renren.modules.hotel.vo.UserCoupons;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Mapper;

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
	Page<UserCoupons> userCashCouponsPage(Page<UserCoupons> page, Long userId);

}
