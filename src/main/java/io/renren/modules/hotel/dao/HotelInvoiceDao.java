package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelInvoiceEntity;

/**
 * 发票
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
@Mapper
public interface HotelInvoiceDao extends BaseMapper<HotelInvoiceEntity> {
	
}
