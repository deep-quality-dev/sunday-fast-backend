package io.renren.modules.hotel.service.impl;

import java.util.HashMap;
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
import cn.hutool.core.util.NumberUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.AssessTagDao;
import io.renren.modules.hotel.dao.HotelAssessDao;
import io.renren.modules.hotel.dao.HotelMemberCollectDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.AssessTagEntity;
import io.renren.modules.hotel.entity.HotelAssessEntity;
import io.renren.modules.hotel.entity.HotelBrandEntity;
import io.renren.modules.hotel.entity.HotelBrandTypeEntity;
import io.renren.modules.hotel.entity.HotelMemberCollectEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelTopicEntity;
import io.renren.modules.hotel.form.SellerApplyForm;
import io.renren.modules.hotel.service.HotelBrandService;
import io.renren.modules.hotel.service.HotelBrandTypeService;
import io.renren.modules.hotel.service.HotelFacilityService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelTopicService;
import io.renren.modules.hotel.vo.FacilityVo;
import io.renren.modules.hotel.vo.HotelBrandTypeVo;
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
	private AssessTagDao assessTagDao;

	@Autowired
	private HotelTopicService hotelTopicService;

	@Autowired
	private HotelBrandService hotelBrandService;

	@Autowired
	private HotelFacilityService hotelFacilityService;

	@Autowired
	private HotelBrandTypeService hotelBrandTypeService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object state = params.get("state");
		Object name = params.get("name");
		Object linkName = params.get("linkName");
		Object linkTel = params.get("linkTel");
		IPage<HotelSellerEntity> page = this.page(new Query<HotelSellerEntity>().getPage(params), new QueryWrapper<HotelSellerEntity>().eq("state", state).like(name != null, "name", name).like(linkName != null, "link_name", linkName).like(linkTel != null, "link_tel", linkTel));

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
		List<AssessTagEntity> tags = assessTagDao.hotelTags(sellerId);
		hotelInfo.setTags(tags);
		HotelBrandEntity hotelBrandEntity = hotelBrandService.getById(hotelSellerEntity.getBrandId());
		if (null != hotelBrandEntity) {
			HotelBrandTypeEntity brandTypeEntity = hotelBrandTypeService.getById(hotelBrandEntity.getTypeId());
			hotelInfo.setLevelType(brandTypeEntity.getName());
		}
		log.debug("获取酒店信息--end,result:{}", JSON.toJSONString(hotelInfo));
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

	@Override
	public void sellerApply(SellerApplyForm sellerApplyForm) {
		HotelSellerEntity hotelSellerEntity = new HotelSellerEntity();
		hotelSellerEntity.setState(1);
		baseMapper.insert(hotelSellerEntity);

	}

	@Override
	public Map<String, Object> filterData() {
		Map<String, Object> data = new HashMap<String, Object>();
		List<HotelTopicEntity> hotelTopicEntities = hotelTopicService.list();
		data.put("topic", hotelTopicEntities);
		List<HotelBrandTypeVo> brands = hotelBrandService.hotelBrandWithType();
		data.put("brand", brands);
		List<FacilityVo> facilityVos = hotelFacilityService.hotelFacility(1);
		data.put("facility", facilityVos);
		return data;
	}

// 1待审核,2通过，3拒绝
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditPass(Long id) {
		HotelSellerEntity hotelSellerEntity = this.getById(id);
		if (null == hotelSellerEntity) {
			throw new RRException("未找到数据");
		}
		hotelSellerEntity.setState(2);
		this.updateById(hotelSellerEntity);
	}

	// 1待审核,2通过，3拒绝
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditRefuse(Long id) {
		HotelSellerEntity hotelSellerEntity = this.getById(id);
		if (null == hotelSellerEntity) {
			throw new RRException("未找到数据");
		}
		hotelSellerEntity.setState(3);
		this.updateById(hotelSellerEntity);
	}

}