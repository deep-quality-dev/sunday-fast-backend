package io.renren.modules.hotel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.hotel.dto.HotelSellerMemberDto;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.vo.VipCardItemVo;

/**
 * 会员等级表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:36
 */
@Mapper
public interface HotelMemberLevelDao extends BaseMapper<HotelMemberLevelEntity> {

	/**
	 * 用户会员卡列表
	 * 
	 * @param userId
	 * @return
	 */
	List<VipCardItemVo> userCardList(Long userId);

	/**
	 * 商家会员卡列表
	 * 
	 * @param levelId
	 * @param sellerId
	 * @return
	 */
	List<VipCardItemVo> seletSellerVipsList(Long levelId, Long sellerId);

	/**
	 * 用户卡片详情
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	VipCardItemVo userCardDetailById(Long userId, Long sellerId);

	/**
	 * 商家会员列表
	 * @param page
	 * @param params
	 * @return
	 */
	IPage<HotelSellerMemberDto> pageList(Page<HotelSellerMemberDto> page, @Param("params") Map<String, Object> params);

}
