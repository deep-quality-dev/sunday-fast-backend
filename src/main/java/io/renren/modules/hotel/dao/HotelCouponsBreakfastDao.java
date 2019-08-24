package io.renren.modules.hotel.dao;

import io.renren.modules.hotel.entity.HotelCouponsBreakfastEntity;
import io.renren.modules.hotel.vo.UserCoupons;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Mapper;

/**
 * 早餐券
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 22:22:25
 */
@Mapper
public interface HotelCouponsBreakfastDao extends BaseMapper<HotelCouponsBreakfastEntity> {

	/**
	 * 用户早餐券
	 * 
	 * @param page
	 * @param userId
	 * @return
	 */
	Page<UserCoupons> userBreakfastCoupons(Page<UserCoupons> page, Long userId);

}
