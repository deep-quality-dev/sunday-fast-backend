package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelRoomNumEntity;

/**
 * 房量
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:34
 */
@Mapper
public interface HotelRoomNumDao extends BaseMapper<HotelRoomNumEntity> {

	/**
	 * 更新房量
	 * @param hotelRoomNumEntity
	 * @param roomNum
	 */
	void updateRoomNum(HotelRoomNumEntity params, int roomNum);

}
