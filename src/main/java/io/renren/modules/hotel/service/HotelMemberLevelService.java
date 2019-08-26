package io.renren.modules.hotel.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.form.BecomeVipForm;
import io.renren.modules.hotel.form.BindVipCardForm;
import io.renren.modules.hotel.vo.VipCardInfoVo;
import io.renren.modules.hotel.vo.VipCardItemVo;

/**
 * 会员等级表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
public interface HotelMemberLevelService extends IService<HotelMemberLevelEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 绑定实体卡
	 * 
	 * @param userId
	 * @param vipCardForm
	 */
	void bindCard(Long userId, BindVipCardForm vipCardForm);

	/**
	 * 成为会员
	 * 
	 * @param userId
	 * @param becomeVipForm
	 */
	void becomeVip(Long userId, BecomeVipForm becomeVipForm);

	/**
	 * 检查会员状态
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	int checkVipStatus(Long userId, Long sellerId);

	/**
	 * 会员卡信息
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	VipCardInfoVo vipCardInfo(Long userId, Long sellerId);

	/**
	 * 会员卡列表
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	List<VipCardItemVo> vipCardList(Long userId, Long sellerId);

	/**
	 * 用户会员卡列表
	 * @param userId
	 * @return
	 */
	List<VipCardItemVo> userCardlist(Long userId);
}
