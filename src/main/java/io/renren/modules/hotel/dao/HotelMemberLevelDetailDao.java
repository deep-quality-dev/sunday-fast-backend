package io.renren.modules.hotel.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;

/**
 * 会员卡详情
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-17 19:46:29
 */
@Mapper
public interface HotelMemberLevelDetailDao extends BaseMapper<HotelMemberLevelDetailEntity> {

	/**
	 * 扣减余额
	 * 
	 * @param hotelMemberLevelDetailEntity
	 * @param totalCost
	 */
	void updateBanlance(HotelMemberLevelDetailEntity params, BigDecimal totalCost);

	/**
	 * 扣减积分
	 * 
	 * @param hotelMemberLevelDetailEntity
	 * @param totalCost
	 */
	void updateintegral(HotelMemberLevelDetailEntity params, BigDecimal totalCost);

	/**
	 * 增加积分
	 * 
	 * @param hotelMemberLevelDetailEntity
	 * @param totalCost
	 */
	void addIntegral(HotelMemberLevelDetailEntity params, BigDecimal totalCost);

	/**
	 * 添加余额
	 * 
	 * @param hotelMemberLevelDetailEntity
	 * @param totalCost
	 */
	void addBalance(HotelMemberLevelDetailEntity params, BigDecimal totalCost);

}
