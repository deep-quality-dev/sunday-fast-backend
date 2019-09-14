package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelConsumptionRecordEntity;

/**
 * 会员卡消费明细
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-28 22:24:24
 */
@Mapper
public interface HotelConsumptionRecordDao extends BaseMapper<HotelConsumptionRecordEntity> {
	
}
