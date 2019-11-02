package io.renren.modules.hotel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.vo.UserCoupons;
import io.renren.modules.hotel.vo.WalletDataVo;

/**
 * 优惠券
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:38
 */
@Mapper
public interface HotelCouponsDao extends BaseMapper<HotelCouponsEntity> {

	/**
	 * 钱包主页数据
	 * 
	 * @param userId
	 * @return
	 */
	WalletDataVo walletData(Long userId);

	/**
	 * 用户优惠券
	 * 
	 * @param page
	 * @param userId
	 * @return
	 */
	Page<UserCoupons> userCoupons(Page<UserCoupons> page, int status, Long userId);

	/**
	 * 可用免房券
	 * 
	 * @param userId
	 * @param sellerId
	 * @param roomId
	 * @return
	 */
	List<UserCoupons> canUseFreeRoomCoupons(Long userId, Long sellerId, Long roomId);

}
