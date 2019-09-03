package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.vo.CommentItemVo;

/**
 * 评价表
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
@Mapper
public interface HotelAssessDao extends BaseMapper<HotelAssessEntity> {

	/**
	 * 酒店评论列表
	 * 
	 * @param page
	 * @param sellerId
	 * @return
	 */
	Page<CommentItemVo> hotelCommnetList(Page<CommentItemVo> page, Long sellerId);

	/**
	 * 商品评论列表
	 * 
	 * @param page
	 * @param goodsId
	 * @return
	 */
	Page<CommentItemVo> goodsCommnetList(Page<CommentItemVo> page, Long goodsId);

	/**
	 * 就定评价平均分
	 * 
	 * @param sellerId
	 * @return
	 */
	double avgScore(Long sellerId);

}
