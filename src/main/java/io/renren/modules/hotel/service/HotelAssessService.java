package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.form.CommentForm;
import io.renren.modules.hotel.vo.CommentItemVo;

/**
 * 评价表
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:39
 */
public interface HotelAssessService extends IService<HotelAssessEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 添加评论
	 * 
	 * @param userId
	 * @param commentForm
	 */
	void addAssess(Long userId, CommentForm commentForm);

	/**
	 * 酒店评论列表
	 * @param page
	 * @param limie
	 * @param sellerId 
	 * @return
	 */
	Page<CommentItemVo> hotelCommnetList(Page<CommentItemVo> page, Long sellerId,String type);

	/**
	 * 商品评论列表
	 * @param page
	 * @param limie
	 * @param goodsId
	 * @return
	 */
	Page<CommentItemVo> goodsCommnetList(Page<CommentItemVo> page, Long goodsId);

	/**
	 * 酒店评论总数
	 * @param sellerId
	 * @return
	 */
	Map<String, Object> hotelCount(Long sellerId);
}
