package io.renren.modules.hotel.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelSellerEntity;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@Mapper
public interface HotelSellerDao extends BaseMapper<HotelSellerEntity> {

    Page hotelPage(Page<HotelItemVo> page, HotelSearchCondition params);
}
