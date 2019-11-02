package io.renren.modules.hotel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.HotelOrderStatus;
import io.renren.modules.hotel.dao.AssessTagRelDao;
import io.renren.modules.hotel.dao.HotelAssessDao;
import io.renren.modules.hotel.entity.AssessTagRelEntity;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.form.CommentForm;
import io.renren.modules.hotel.service.HotelAssessService;
import io.renren.modules.hotel.service.HotelOrderService;
import io.renren.modules.hotel.vo.CommentItemVo;

@Service("hotelAssessService")
public class HotelAssessServiceImpl extends ServiceImpl<HotelAssessDao, HotelAssessEntity> implements HotelAssessService {

	@Autowired
	private HotelOrderService hotelOrderService;

	@Autowired
	private HotelAssessDao hotelAssessDao;

	@Autowired
	private AssessTagRelDao assessTagRelDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelAssessEntity> page = this.page(new Query<HotelAssessEntity>().getPage(params), new QueryWrapper<HotelAssessEntity>().eq(sellerId != null, "seller_id", sellerId));

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addAssess(Long userId, CommentForm commentForm) {
		Long sellerId = null;
		if (1 == commentForm.getType()) {
			HotelOrderEntity hotelOrderEntity = hotelOrderService.getById(commentForm.getBizId());
			hotelOrderEntity.setCommentFlag(1);
			hotelOrderEntity.setStatus(HotelOrderStatus.COMPLETE);
			hotelOrderService.updateById(hotelOrderEntity);
			sellerId = hotelOrderEntity.getSellerId();
		}
		if (2 == commentForm.getType()) {
			sellerId = null;
		}
		HotelAssessEntity hotelAssessEntity = new HotelAssessEntity();
		BeanUtil.copyProperties(commentForm, hotelAssessEntity);
		hotelAssessEntity.setSellerId(sellerId);
		hotelAssessEntity.setTime(DateTime.now().getTime());
		hotelAssessEntity.setStatus(1);
		hotelAssessEntity.setUserId(userId);
		hotelAssessEntity.setOrderId(commentForm.getBizId());
		baseMapper.insert(hotelAssessEntity);
		if (CollectionUtil.isNotEmpty(commentForm.getTagList())) {
			List<Long> tags = commentForm.getTagList();
			tags.forEach((Long id) -> {
				AssessTagRelEntity assessTagRelEntity = new AssessTagRelEntity();
				assessTagRelEntity.setTagId(id);
				assessTagRelEntity.setAssessId(hotelAssessEntity.getId());
				assessTagRelDao.insert(assessTagRelEntity);
			});
		}
	}

	@Override
	public Page<CommentItemVo> hotelCommnetList(Page<CommentItemVo> page, Long sellerId, String type) {
		Page<CommentItemVo> pageResult = baseMapper.hotelCommnetList(page, sellerId, type);
		List<CommentItemVo> commentItemVos = pageResult.getRecords();
		for (CommentItemVo commentItemVo : commentItemVos) {
			commentItemVo.setDate(DateUtil.format(DateUtil.date(Long.valueOf(commentItemVo.getDate())), "yyyy-MM-dd HH:mm:ss"));
		}
		return pageResult;
	}

	@Override
	public Page<CommentItemVo> goodsCommnetList(Page<CommentItemVo> page, Long goodsId) {
		Page<CommentItemVo> pageResult = baseMapper.goodsCommnetList(page, goodsId);
		return pageResult;
	}

	@Override
	public Map<String, Object> hotelCount(Long sellerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("good", baseMapper.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().gt(HotelAssessEntity::getScore, 3).eq(HotelAssessEntity::getSellerId, sellerId)));
		map.put("bad", baseMapper.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().lt(HotelAssessEntity::getScore, 3).eq(HotelAssessEntity::getSellerId, sellerId)));
		map.put("img", baseMapper.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().isNotNull(HotelAssessEntity::getImg).eq(HotelAssessEntity::getSellerId, sellerId)));
		double score = hotelAssessDao.avgScore(sellerId);
		map.put("score", score);
		return map;
	}

}