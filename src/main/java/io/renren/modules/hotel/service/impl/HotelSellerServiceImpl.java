package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelAssessDao;
import io.renren.modules.hotel.dao.HotelMemberCollectDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.entity.HotelMemberCollectEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.map.GaodeAPI;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.HotelInfo;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import io.renren.modules.hotel.vo.HotelSearchVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelSellerService")
public class HotelSellerServiceImpl extends ServiceImpl<HotelSellerDao, HotelSellerEntity> implements HotelSellerService {

	@Autowired
	private HotelMemberCollectDao hotelMemberCollectDao;

	@Autowired
	private HotelAssessDao hotelAssessDao;

	@Autowired
	private HotelRoomService hotelRoomService;

	@Autowired
	private HotelRoomMoneyService hotelRoomMoneyService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object state = params.get("state");
		IPage<HotelSellerEntity> page = this.page(new Query<HotelSellerEntity>().getPage(params), new QueryWrapper<HotelSellerEntity>().eq("state", state));

		return new PageUtils(page);
	}

	@Override
	public HotelInfo sellerInfo(Long userId, Long sellerId) {
		log.info("获取酒店信息--start,sellerId", sellerId);
		HotelInfo hotelInfo = new HotelInfo();
		HotelSellerEntity hotelSellerEntity = this.getById(sellerId);
		BeanUtil.copyProperties(hotelSellerEntity, hotelInfo);
		hotelInfo.setImg(hotelSellerEntity.getImg());
		HotelMemberCollectEntity hotelMemberCollectEntity = hotelMemberCollectDao.selectOne(Wrappers.<HotelMemberCollectEntity>lambdaQuery().eq(HotelMemberCollectEntity::getBizId, sellerId).eq(HotelMemberCollectEntity::getBizType, 1).eq(HotelMemberCollectEntity::getUserId, userId));
		if (null != hotelMemberCollectEntity) {
			hotelInfo.setCollect(1);
		}
		int commentCount = hotelAssessDao.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().eq(HotelAssessEntity::getSellerId, sellerId));
		hotelInfo.setCommentCount(commentCount);
		// 好评
		int goodsCommentCount = hotelAssessDao.selectCount(Wrappers.<HotelAssessEntity>lambdaQuery().eq(HotelAssessEntity::getSellerId, sellerId).ge(HotelAssessEntity::getScore, 3));
		if (commentCount > 0) {
			hotelInfo.setCommentRate(NumberUtil.decimalFormat("#.##%", NumberUtil.div(goodsCommentCount, commentCount)));
		}
		// 平均评分
		double score = hotelAssessDao.avgScore(sellerId);
		hotelInfo.setScore(NumberUtil.round(score, 2));
		log.info("获取酒店信息--end,result:{}", JSON.toJSONString(hotelInfo));
		return hotelInfo;
	}

	@Override
	public Page<HotelItemVo> hotelPage(Long userId, HotelSearchCondition params, Page<HotelItemVo> page) {
		double latitude = Double.valueOf(params.getLonLat().split(",")[1]);
		double longitude = Double.valueOf(params.getLonLat().split(",")[0]);
		params.setLatitude(latitude);
		params.setLongitude(longitude);
		Page<HotelItemVo> pageResult = baseMapper.hotelPage(page, params);
		List<HotelItemVo> hotelItemVos = pageResult.getRecords();
		for (HotelItemVo hotelItemVo : hotelItemVos) {
			hotelItemVo.setKm(NumberUtil.round(hotelItemVo.getDistance(), 2));
			double score = hotelAssessDao.avgScore(hotelItemVo.getId());
			hotelItemVo.setScore(NumberUtil.round(score, 2));
		}
		pageResult.setRecords(hotelItemVos);
		return pageResult;
	}

	@Override
	public Page<HotelSearchVo> search(String kw, Page<HotelSearchVo> page) {
		Page<HotelSearchVo> pageResult = baseMapper.search(page, kw);
		return pageResult;
	}

	@Override
	@Transactional
	public void test() {
		List<HotelSellerEntity> hotelSellerEntities = this.list();
		for (HotelSellerEntity hotelSellerEntity : hotelSellerEntities) {
			hotelSellerEntity.setLat(hotelSellerEntity.getCoordinates().split(",")[1]);
			hotelSellerEntity.setLnt(hotelSellerEntity.getCoordinates().split(",")[0]);
			baseMapper.updateById(hotelSellerEntity);
		}
//		HotelRoomEntity hotelRoomEntity = hotelRoomService.list().get(0);
//		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.list().get(0);
//		String result = HttpUtil.get("https://ihotel.meituan.com/hbsearch/HotelSearch?utm_medium=touch&version_name=999.9&platformid=1&cateId=20&newcate=1&limit=20&offset=120&cityId=30&ci=30&startendday=20190903~20190903&startDay=20190903&endDay=20190903&q=%E5%8D%97%E5%B1%B1%E5%8C%BA&ste=_b400202&mypos=22.531142%2C113.943757&attr_28=129&sort=defaults&userid=331990339&uuid=12E6ABEB1C73C7E218E45A65DB06E21E752EB811B3FC9AD97E63AF23CB2D332B&accommodationType=1&lat=22.531142&lng=113.943757&keyword=%E5%8D%97%E5%B1%B1%E5%8C%BA");
//		JSONObject js = JSON.parseObject(result).getJSONObject("data");
//		JSONArray sellerList = js.getJSONArray("searchresult");
//		for (int i = 0; i < sellerList.size(); i++) {
//			JSONObject obj = sellerList.getJSONObject(i);
//			HotelSellerEntity hotelSellerEntity = new HotelSellerEntity();
//			hotelSellerEntity.setAddress(obj.getString("addr"));
//			hotelSellerEntity.setUserId(0L);
//			hotelSellerEntity.setCoordinates(obj.getString("lng") + "," + obj.getString("lat"));
//			hotelSellerEntity.setStar("5");
//			hotelSellerEntity.setState(2);
//			hotelSellerEntity.setName(obj.getString("name"));
//			hotelSellerEntity.setOwner(1);
//			String img = obj.getString("frontImg");
//			hotelSellerEntity.setEwmLogo(img.replace("w.h", "500.500"));
//			hotelSellerEntity.setLinkName("improt_" + i);
//			hotelSellerEntity.setLinkTel("12345678");
//			this.save(hotelSellerEntity);
//			HotelRoomEntity roomEntity = new HotelRoomEntity();
//			BeanUtil.copyProperties(hotelRoomEntity, roomEntity, "id","sellerId");
//			roomEntity.setSellerId(hotelSellerEntity.getId());
//			hotelRoomService.save(roomEntity);
//			HotelRoomMoneyEntity roomMoneyEntity = new HotelRoomMoneyEntity();
//			BeanUtil.copyProperties(hotelRoomMoneyEntity, roomMoneyEntity, "id","sellerId");
//			roomMoneyEntity.setRoomId(hotelRoomEntity.getId());
//			roomMoneyEntity.setSellerId(hotelSellerEntity.getId());
//			hotelRoomMoneyService.save(roomMoneyEntity);
//		}

	}

}