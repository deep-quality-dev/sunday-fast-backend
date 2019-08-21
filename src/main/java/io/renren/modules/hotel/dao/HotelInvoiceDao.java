package io.renren.modules.hotel.dao;

import io.renren.modules.hotel.entity.HotelInvoiceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
