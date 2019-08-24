package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelScoreEntity;

/**
 * 积分明细表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:32
 */
public interface HotelScoreService extends IService<HotelScoreEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 签到
	 * 
	 * @param userId   用户ID
	 * @param sellerId 商家ID
	 */
	boolean signIn(Long sellerId, Long userId);

	/**
	 * 积分交易
	 * 
	 * @param userId
	 * @param score
	 * @param type 
	 * @param note
	 */
	void transactionScore(Long userId, int type, int score, String note);

	/**
	 * 积分列表
	 * @param sellerId
	 * @param userId
	 * @param params
	 * @return
	 */
	PageUtils signInList(Long userId, Map<String, Object> params);
}
