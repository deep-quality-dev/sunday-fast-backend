package io.renren.modules.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelScoreDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelScoreEntity;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.vo.HotelScore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelScoreService")
public class HotelScoreServiceImpl extends ServiceImpl<HotelScoreDao, HotelScoreEntity> implements HotelScoreService {

	@Autowired
	private HotelMemberService hotelMemberService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelScoreEntity> page = this.page(new Query<HotelScoreEntity>().getPage(params), new QueryWrapper<HotelScoreEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional
	public boolean signIn(Long sellerId, Long userId) {
		log.info("用户签到--start,sellerId:{},userId:{}");
		DateTime time = DateUtil.parse(DateUtil.now(), "YYYY-MM-DD");
		HotelScoreEntity hotelScoreEntity = this.getOne(new QueryWrapper<HotelScoreEntity>().eq("seller_id", sellerId).eq("user_id", userId).eq("time", time.getTime()));
		if (null == hotelScoreEntity) {
			log.info("用户签到--time:{}", time.getTime());
			hotelScoreEntity = new HotelScoreEntity();
			hotelScoreEntity.setCreateTime(DateUtil.date());
			hotelScoreEntity.setNote("每日签到");
			hotelScoreEntity.setScore(5);
			hotelScoreEntity.setTime(time.getTime());
			hotelScoreEntity.setSellerId(sellerId);
			hotelScoreEntity.setUserId(userId);
			hotelScoreEntity.setType(1);
			this.save(hotelScoreEntity);
			hotelMemberService.updateUserScore(userId, hotelScoreEntity.getScore());
			log.info("用户签到--end");
			return Boolean.TRUE;

		} else {
			log.info("用户签到--已经签过到");
			return Boolean.FALSE;
		}
	}

	public static void main(String[] args) {
		DateTime time = DateUtil.parse(DateUtil.now(), "YYYY-MM-DD");
		System.out.println(time.getTime());
	}

	@Override
	@Transactional
	public void transactionScore(Long userId, int score, int type, String note) {
		log.info("添加积分流水--start,userId:{},score:{},type:{},note:{}", userId, score, type, note);
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelScoreEntity hotelScoreEntity = new HotelScoreEntity();
		hotelScoreEntity.setScore(score);
		hotelScoreEntity.setCreateTime(DateUtil.date());
		hotelScoreEntity.setUserId(userId);
		hotelScoreEntity.setSellerId(hotelMemberEntity.getSellerId());
		hotelScoreEntity.setType(type);
		hotelScoreEntity.setNote(note);
		this.save(hotelScoreEntity);
		// 更新用户积分
		hotelMemberService.updateUserScore(userId, score);
		log.info("添加积分流水--end");
	}

	@Override
	public PageUtils signInList(Long userId, Map<String, Object> params) {
		log.info("用户积分列表--start,userId:{},params:{}", userId, JSON.toJSONString(params));
		List<HotelScore> hotelScores = new ArrayList<HotelScore>();
		QueryWrapper<HotelScoreEntity> queryWrapper = new QueryWrapper<HotelScoreEntity>();
		queryWrapper.eq("user_id", userId);
		queryWrapper.orderByDesc("create_time");
		IPage<HotelScoreEntity> page = this.page(new Query<HotelScoreEntity>().getPage(params), queryWrapper);
		List<HotelScoreEntity> hotelScoreEntities = page.getRecords();
		HotelScore hotelScore = null;
		for (HotelScoreEntity hotelScoreEntity : hotelScoreEntities) {
			hotelScore = new HotelScore();
			BeanUtil.copyProperties(hotelScoreEntity, hotelScore);
			hotelScores.add(hotelScore);
		}
		log.info("用户积分列表--end");
		return new PageUtils(hotelScores, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public Page<HotelScore> scoreList(Long userId, Long cardId, Page<HotelScore> page) {
		List<HotelScore> hotelScores = new ArrayList<HotelScore>();
		IPage<HotelScoreEntity> pageParams = new Page<HotelScoreEntity>(page.getCurrent(), page.getSize());
		IPage<HotelScoreEntity> pageResult = this.page(pageParams, Wrappers.<HotelScoreEntity>lambdaQuery().eq(HotelScoreEntity::getCardId, cardId).eq(HotelScoreEntity::getUserId, userId));
		HotelScore hotelScore = null;
		List<HotelScoreEntity> hotelScoreEntities = pageResult.getRecords();
		for (HotelScoreEntity hotelScoreEntity : hotelScoreEntities) {
			hotelScore = new HotelScore();
			BeanUtil.copyProperties(hotelScoreEntity, hotelScore);
			hotelScores.add(hotelScore);
		}
		Page<HotelScore> scorePageResult = new Page<HotelScore>(page.getCurrent(), page.getSize());
		scorePageResult.setRecords(hotelScores);
		scorePageResult.setTotal(pageResult.getTotal());
		return scorePageResult;
	}

}