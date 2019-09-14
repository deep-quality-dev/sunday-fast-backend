package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.hotel.entity.HotelContactsEntity;

/**
 * 联系人
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 23:07:29
 */
@Mapper
public interface HotelContactsDao extends BaseMapper<HotelContactsEntity> {

	/**
	 * 最精使用联系人
	 * 
	 * @param userId
	 * @return
	 */
	HotelContactsEntity latelyContact(Long userId);

}
