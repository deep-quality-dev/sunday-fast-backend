package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.hotel.entity.HotelRechargeEntity;
import io.renren.modules.hotel.vo.CardConsumptionVo;

/**
 * 充值表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@Mapper
public interface HotelRechargeDao extends BaseMapper<HotelRechargeEntity> {

	/**
	 * 卡片消费记录
	 * @param page
	 * @param userId
	 * @param cardId
	 * @return
	 */
	Page<CardConsumptionVo> consumptionRecord(Page<CardConsumptionVo> page, Long userId, Long cardId);

}
