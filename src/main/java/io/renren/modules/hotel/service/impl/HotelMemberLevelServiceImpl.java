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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.config.WxPayConfiguration;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.form.BecomeVipForm;
import io.renren.modules.hotel.form.BindVipCardForm;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.hotel.service.HotelMemberLevelService;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelWxConfigService;
import io.renren.modules.hotel.vo.VipCardItemVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelMemberLevelService")
public class HotelMemberLevelServiceImpl extends ServiceImpl<HotelMemberLevelDao, HotelMemberLevelEntity> implements HotelMemberLevelService {

	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	@Autowired
	private HotelMemberLevelService hotelMemberLevelService;

	@Autowired
	private HotelSellerService hotelSellerService;

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private HotelSellerDao hotelSellerDao;

	@Autowired
	private HotelWxConfigService hotelWxConfigService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelMemberLevelEntity> page = this.page(new Query<HotelMemberLevelEntity>().getPage(params), new QueryWrapper<HotelMemberLevelEntity>().eq(sellerId != null, "seller_id", sellerId));

		return new PageUtils(page);
	}

	@Override
	public void bindCard(Long userId, BindVipCardForm vipCardForm) {
		// TODO 查询酒店系统是否有会员

	}

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public WxPayMpOrderResult becomeVip(Long userId, BecomeVipForm becomeVipForm) {
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelMemberLevelEntity hotelMemberLevelEntity = hotelMemberLevelService.getById(becomeVipForm.getLevelId());
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, hotelMemberLevelEntity.getSellerId()));
		if (null != hotelMemberLevelDetailEntity) {
			log.error("会员办卡--卡片变更，userId:{},parms:{}", userId, JSON.toJSONString(becomeVipForm));
			hotelMemberLevelDetailEntity.setLevelId(becomeVipForm.getLevelId());
			hotelMemberLevelDetailService.updateById(hotelMemberLevelDetailEntity);
			// TODO 消费记录修改，积分明细修改
		} else {
			hotelMemberLevelDetailEntity = new HotelMemberLevelDetailEntity();
			BeanUtil.copyProperties(becomeVipForm, hotelMemberLevelDetailEntity);
			hotelMemberLevelDetailEntity.setMemberId(userId);
			hotelMemberLevelDetailEntity.setStatus(1);
//			if (1 == hotelMemberLevelEntity.getPayFlag()) {
//				hotelMemberLevelDetailEntity.setStatus(-1);
//			}
			hotelMemberLevelDetailEntity.setSellerId(hotelMemberLevelEntity.getSellerId());
			hotelMemberLevelDetailEntity.setMobile(hotelMemberEntity.getTel());
			hotelMemberLevelDetailService.save(hotelMemberLevelDetailEntity);
		}
		HotelSellerEntity hotelSellerEntity = hotelSellerDao.selectById(hotelMemberLevelEntity.getSellerId());
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>().eq("seller_id", hotelMemberLevelEntity.getSellerId()));
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setBody(hotelSellerEntity.getName() + "(办卡)");
		wxPayUnifiedOrderRequest.setOutTradeNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS"));
		wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
		wxPayUnifiedOrderRequest.setNotifyUrl("https://hotelapi.xqtinfo.cn/pay/" + hotelWxConfigEntity.getAppId() + "/notify/order");
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setTotalFee(1);
		wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
		WxPayMpOrderResult mpOrderResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).createOrder(wxPayUnifiedOrderRequest);
		log.info("调用微信统一下单--start,result:{}", JSON.toJSONString(mpOrderResult));
		return mpOrderResult;
	}

	@Override
	public int checkVipStatus(Long userId, Long sellerId) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>query().lambda().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		return hotelMemberLevelDetailEntity == null ? -1 : 1;
	}

	@Override
	public VipCardItemVo vipCardInfo(Long userId, Long sellerId) {
		VipCardItemVo cardInfoVo = new VipCardItemVo();
		cardInfoVo = baseMapper.userCardDetailById(userId, sellerId);
		return cardInfoVo;
	}

	@Override
	public List<VipCardItemVo> vipCardList(Long userId, Long sellerId) {
		List<VipCardItemVo> cardItemVos = new ArrayList<VipCardItemVo>();
		Long levelId = null;
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		if (null != hotelMemberLevelDetailEntity) {
			levelId = hotelMemberLevelDetailEntity.getLevelId();
		}
		cardItemVos = baseMapper.seletSellerVipsList(levelId, sellerId);
		return cardItemVos;
	}

	@Override
	public List<VipCardItemVo> userCardlist(Long userId) {
		return baseMapper.userCardList(userId);
	}

	@Override
	public BecomeVipForm getSellerCardInfo(Long userId, Long sellerId) {
		BecomeVipForm becomeVipForm = new BecomeVipForm();
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, userId).eq(HotelMemberLevelDetailEntity::getSellerId, sellerId));
		if (null == hotelMemberLevelDetailEntity) {
			return null;
		}
		becomeVipForm.setCertificate(hotelMemberLevelDetailEntity.getCertificate());
		becomeVipForm.setCertificateNo(hotelMemberLevelDetailEntity.getCertificateNo());
		becomeVipForm.setName(hotelMemberLevelDetailEntity.getName());
		becomeVipForm.setLevelId(hotelMemberLevelDetailEntity.getLevelId());
		return becomeVipForm;
	}

}