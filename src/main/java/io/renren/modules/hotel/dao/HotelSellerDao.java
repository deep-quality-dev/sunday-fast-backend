package io.renren.modules.hotel.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import io.renren.modules.hotel.vo.HotelSearchVo;

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

	/**
	 * 酒店分页列表
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	Page<HotelItemVo> hotelPage(Page<HotelItemVo> page, HotelSearchCondition params);

	/**
	 * 酒店搜索
	 * 
	 * @param page
	 * @param kw
	 * @return
	 */
	Page<HotelSearchVo> search(Page<HotelSearchVo> page, String kw);
}
