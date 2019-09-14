package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelRoomEntity;

/**
 * 房型信息
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
@Mapper
public interface HotelRoomDao extends BaseMapper<HotelRoomEntity> {

	/**
	 * 更新房型总数量
	 * @param hotelRoomEntity
	 * @param roomNum
	 */
	void updateRoomNum(HotelRoomEntity params, int roomNum);

}
