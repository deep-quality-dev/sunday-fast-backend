package io.renren.modules.hotel.dao;

import java.util.Date;

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
	 * 
	 * @param hotelRoomNumEntity
	 * @param roomNum
	 */
	void updateRoomNum(HotelRoomNumEntity params, int roomNum);

	/**
	 * 查询某天，某房型是否有房
	 * 
	 * @param id
	 * @param time
	 */
	int hasRoomNumWithDay(Long roomId, long time);

	/**
	 * 查询区间日期，某房型是否有房
	 * 
	 * @param id
	 * @param time
	 */
	int hasRoomNumWithBetweenDay(Long roomId, long startTime, long endTime);

	/**
	 * 查询区间日期，某价格房间是否有房
	 * 
	 * @param moneyId
	 * @param time
	 */
	int hasRoomMoneyNumWithBetweenDay(Long moneyId, long startTime, long endTime);

}
