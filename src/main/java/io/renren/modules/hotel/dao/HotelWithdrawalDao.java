package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelWithdrawalEntity;

/**
 * 提现记录
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@Mapper
public interface HotelWithdrawalDao extends BaseMapper<HotelWithdrawalEntity> {

}
