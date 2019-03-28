package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelEarningsEntity;

/**
 * 佣金收益表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:37
 */
@Mapper
public interface HotelEarningsDao extends BaseMapper<HotelEarningsEntity> {

}
