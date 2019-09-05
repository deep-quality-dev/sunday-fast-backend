package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.HotelWxMsgTemplate;
import io.renren.modules.constants.OrderTypeConstants;
import io.renren.modules.hotel.config.WxMpConfiguration;
import io.renren.modules.hotel.config.WxPayConfiguration;
import io.renren.modules.hotel.dao.HotelConsumptionRecordDao;
import io.renren.modules.hotel.dao.HotelMemberDao;
import io.renren.modules.hotel.dao.HotelMemberLevelDao;
import io.renren.modules.hotel.dao.HotelMemberLevelDetailDao;
import io.renren.modules.hotel.dao.HotelRechargeConfigDao;
import io.renren.modules.hotel.dao.HotelRechargeDao;
import io.renren.modules.hotel.dao.HotelSellerDao;
import io.renren.modules.hotel.dto.HotelRechargeDto;
import io.renren.modules.hotel.entity.HotelConsumptionRecordEntity;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.entity.HotelRechargeConfigEntity;
import io.renren.modules.hotel.entity.HotelRechargeEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.entity.HotelWxTemplateEntity;
import io.renren.modules.hotel.form.CardRechargeForm;
import io.renren.modules.hotel.service.HotelRechargeService;
import io.renren.modules.hotel.service.HotelWxConfigService;
import io.renren.modules.hotel.service.HotelWxTemplateService;
import io.renren.modules.hotel.vo.CardConsumptionVo;
import io.renren.modules.wx.OrderType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Slf4j
@Service("hotelRechargeService")
public class HotelRechargeServiceImpl extends ServiceImpl<HotelRechargeDao, HotelRechargeEntity> implements HotelRechargeService {

	@Autowired
	private HotelRechargeConfigDao hotelRechargeConfigDao;

	@Autowired
	private HotelMemberLevelDao hotelMemberLevelDao;

	@Autowired
	private HotelConsumptionRecordDao hotelConsumptionRecordDao;

	@Autowired
	private HotelMemberLevelDetailDao hotelMemberLevelDetailDao;

	@Autowired
	private HotelWxTemplateService hotelWxTemplateService;

	@Autowired
	private HotelSellerDao hotelSellerDao;

	@Autowired
	private HotelMemberDao hotelMemberDao;

	@Autowired
	private HotelWxConfigService hotelWxConfigService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		Object outTradeNo = params.get("Object");
		IPage<HotelRechargeEntity> page = this.page(new Query<HotelRechargeEntity>().getPage(params), new QueryWrapper<HotelRechargeEntity>().eq(sellerId != null, "seller_id", sellerId).eq(outTradeNo != null, "out_trade_no", outTradeNo));
		List<HotelRechargeEntity> rechargeEntities = page.getRecords();
		List<HotelRechargeDto> hotelRechargeDtos = rechargeEntities.stream().map((HotelRechargeEntity item) -> {
			HotelRechargeDto hotelRechargeDto = new HotelRechargeDto();
			BeanUtil.copyProperties(item, hotelRechargeDto);
			HotelMemberLevelEntity memberLevelEntity = hotelMemberLevelDao.selectById(item.getCardId());
			hotelRechargeDto.setSellerName(hotelSellerDao.selectById(memberLevelEntity.getSellerId()).getName());
			hotelRechargeDto.setMemberName(hotelMemberDao.selectById(item.getUserId()).getName());
			return hotelRechargeDto;
		}).collect(Collectors.toList());
		IPage<HotelRechargeDto> resultPage = new Page<HotelRechargeDto>();
		resultPage.setCurrent(page.getCurrent());
		resultPage.setPages(page.getPages());
		resultPage.setRecords(hotelRechargeDtos);
		resultPage.setSize(page.getSize());
		resultPage.setTotal(page.getTotal());
		return new PageUtils(resultPage);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@SneakyThrows
	public WxPayMpOrderResult cardRecharge(Long userId, CardRechargeForm cardRechargeForm) {
		BigDecimal amount = new BigDecimal(0);
		HotelRechargeEntity hotelRechargeEntity = new HotelRechargeEntity();
		hotelRechargeEntity.setZsMoney(new BigDecimal(0));
		if (null == cardRechargeForm.getRechargeConfigId() || -1 == cardRechargeForm.getRechargeConfigId()) {
			if (null == cardRechargeForm.getAmount() || cardRechargeForm.getAmount() == BigDecimal.ZERO) {
				throw new RRException("充值金额不能为空");
			}
			amount = cardRechargeForm.getAmount();
		} else {
			HotelRechargeConfigEntity rechargeConfigEntity = hotelRechargeConfigDao.selectById(cardRechargeForm.getRechargeConfigId());
			amount = rechargeConfigEntity.getMoney();
			hotelRechargeEntity.setZsMoney(rechargeConfigEntity.getReturnMoney());
		}
		hotelRechargeEntity.setCzMoney(amount);
		hotelRechargeEntity.setUserId(userId);
		hotelRechargeEntity.setCardId(cardRechargeForm.getCardId());
		hotelRechargeEntity.setTime(System.currentTimeMillis()/1000);
		hotelRechargeEntity.setState(0);
		hotelRechargeEntity.setOutTradeNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS"));
		baseMapper.insert(hotelRechargeEntity);
		// 返回微信支付参数
		HotelMemberLevelEntity hotelMemberLevelEntity = hotelMemberLevelDao.selectById(cardRechargeForm.getCardId());
		HotelMemberEntity hotelMemberEntity = hotelMemberDao.selectById(userId);
		HotelSellerEntity hotelSellerEntity = hotelSellerDao.selectById(hotelMemberLevelEntity.getSellerId());
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>().eq("seller_id", hotelMemberLevelEntity.getSellerId()));
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setBody(hotelSellerEntity.getName() + "(在线充值)");
		wxPayUnifiedOrderRequest.setOutTradeNo(hotelRechargeEntity.getOutTradeNo());
		wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
		wxPayUnifiedOrderRequest.setNotifyUrl("http://hotelapi.xqtinfo.cn/pay/" + hotelWxConfigEntity.getAppId() + "/notify/order");
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setAttach(JSON.toJSONString(new OrderType(OrderTypeConstants.order_recharge)));
		wxPayUnifiedOrderRequest.setTotalFee(1);
		wxPayUnifiedOrderRequest.setSpbillCreateIp(cardRechargeForm.getIp());
		WxPayMpOrderResult mpOrderResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).createOrder(wxPayUnifiedOrderRequest);
		log.info("调用微信统一下单--start,result:{}", JSON.toJSONString(mpOrderResult));
		return mpOrderResult;

	}

	@Override
	public Page<CardConsumptionVo> consumptionRecord(Page<CardConsumptionVo> page, Long userId, Long cardId) {
		return baseMapper.consumptionRecord(page, userId, cardId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cardRechargeHandler(String outTradeNo) {
		HotelRechargeEntity hotelRechargeEntity = baseMapper.selectOne(Wrappers.<HotelRechargeEntity>lambdaQuery().eq(HotelRechargeEntity::getOutTradeNo, outTradeNo));
		if (null != hotelRechargeEntity) {
			hotelRechargeEntity.setState(1);
			baseMapper.updateById(hotelRechargeEntity);
			// 给用户卡片加钱
			HotelMemberLevelDetailEntity memberLevelDetailEntity = hotelMemberLevelDetailDao.selectOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, hotelRechargeEntity.getUserId()).eq(HotelMemberLevelDetailEntity::getLevelId, hotelRechargeEntity.getCardId()));
			memberLevelDetailEntity.setBalance(NumberUtil.add(memberLevelDetailEntity.getBalance(), hotelRechargeEntity.getCzMoney()));
			this.addConsumptionRecord(hotelRechargeEntity.getCzMoney(), hotelRechargeEntity.getCardId(), hotelRechargeEntity.getUserId(), "在线充值");
			if (null != hotelRechargeEntity.getZsMoney()) {
				memberLevelDetailEntity.setBalance(NumberUtil.add(memberLevelDetailEntity.getBalance(), hotelRechargeEntity.getZsMoney()));
				this.addConsumptionRecord(hotelRechargeEntity.getZsMoney(), hotelRechargeEntity.getCardId(), hotelRechargeEntity.getUserId(), "充值赠送");
			}
			hotelMemberLevelDetailDao.updateById(memberLevelDetailEntity);
			// 发送充值成功通知
			ThreadUtil.execute(new Runnable() {
				@Override
				public void run() {
					// 获取酒店取消订单微信消息模板
					List<WxMpTemplateData> data = null;
					HotelMemberEntity hotelMemberEntity = null;
					WxMpTemplateMessage templateMessage = null;
					WxMpService mpService = null;
					HotelWxTemplateEntity hotelWxTemplateEntity = null;
					HotelWxConfigEntity hotelWxConfigEntity = null;
					HotelSellerEntity hotelOrderEntity = hotelSellerDao.selectById(memberLevelDetailEntity.getSellerId());
					hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>().eq("seller_id", memberLevelDetailEntity.getSellerId()));
					if (null != hotelWxConfigEntity) {
						mpService = WxMpConfiguration.getMpServices().get(hotelWxConfigEntity.getAppId());
						data = new ArrayList<>();
						data.add(new WxMpTemplateData("first", "充值成功通知"));
						data.add(new WxMpTemplateData("keyword1", hotelOrderEntity.getName()));
						data.add(new WxMpTemplateData("keyword2", "会员卡充值"));
						data.add(new WxMpTemplateData("keyword3", memberLevelDetailEntity.getCardNo()));
						data.add(new WxMpTemplateData("keyword4", hotelRechargeEntity.getCzMoney().toString()));
						data.add(new WxMpTemplateData("keyword5", hotelRechargeEntity.getZsMoney() != null ? hotelRechargeEntity.getZsMoney().toString() : "0.00"));
						data.add(new WxMpTemplateData("keyword5", DateUtil.format(DateUtil.date(hotelRechargeEntity.getTime()), "yyyy-MM-dd")));
						hotelMemberEntity = hotelMemberDao.selectById(memberLevelDetailEntity.getMemberId());
						hotelWxTemplateEntity = hotelWxTemplateService.getOne(new QueryWrapper<HotelWxTemplateEntity>().eq("seller_id", memberLevelDetailEntity.getSellerId()).eq("type", HotelWxMsgTemplate.CARD_RECHARGE_SUCCESS));
						templateMessage = new WxMpTemplateMessage(hotelMemberEntity.getOpenid(), hotelWxTemplateEntity.getTemplateId(), null, null, data);
						try {
							String result = mpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
							log.info("发送充值成功微信模板消息：result：{}", result);
						} catch (WxErrorException e) {
							e.printStackTrace();
						}
					}

				}
			});
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addConsumptionRecord(BigDecimal amount, Long cardId, Long userId, String note) {
		HotelConsumptionRecordEntity consumptionRecordEntity = new HotelConsumptionRecordEntity();
		consumptionRecordEntity.setAmount(amount);
		consumptionRecordEntity.setCardId(cardId);
		consumptionRecordEntity.setUserId(userId);
		consumptionRecordEntity.setNote(note);
		HotelMemberLevelEntity memberLevelEntity = hotelMemberLevelDao.selectById(cardId);
		consumptionRecordEntity.setSellerId(memberLevelEntity.getSellerId());
		consumptionRecordEntity.setCreateDate(DateUtil.date());
		hotelConsumptionRecordDao.insert(consumptionRecordEntity);
	}

}