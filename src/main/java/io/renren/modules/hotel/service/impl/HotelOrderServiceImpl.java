package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.HotelOrderStatus;
import io.renren.modules.constants.HotelWxMsgTemplate;
import io.renren.modules.constants.OrderTypeConstants;
import io.renren.modules.constants.PayMethodConstants;
import io.renren.modules.hotel.config.WxMaConfiguration;
import io.renren.modules.hotel.config.WxMpConfiguration;
import io.renren.modules.hotel.config.WxPayConfiguration;
import io.renren.modules.hotel.dao.HotelOrderDao;
import io.renren.modules.hotel.entity.HotelContactsEntity;
import io.renren.modules.hotel.entity.HotelCouponsCashEntity;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.entity.HotelOrderRecordEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.entity.HotelWxTemplateEntity;
import io.renren.modules.hotel.form.BuildOrderForm;
import io.renren.modules.hotel.form.CreateOrderForm;
import io.renren.modules.hotel.service.HotelContactsService;
import io.renren.modules.hotel.service.HotelCouponsCashService;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelOrderRecordService;
import io.renren.modules.hotel.service.HotelOrderService;
import io.renren.modules.hotel.service.HotelRechargeService;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomPriceService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelWxConfigService;
import io.renren.modules.hotel.service.HotelWxTemplateService;
import io.renren.modules.hotel.service.TransactionService;
import io.renren.modules.hotel.vo.HotelOrderVo;
import io.renren.modules.hotel.vo.OrderDetail;
import io.renren.modules.wx.OrderType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Slf4j
@Service("hotelOrderService")
public class HotelOrderServiceImpl extends ServiceImpl<HotelOrderDao, HotelOrderEntity> implements HotelOrderService {
	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	@Autowired
	private HotelRechargeService hotelRechargeService;
	@Autowired
	private HotelRoomService hotelRoomService;

	@Autowired
	private HotelRoomMoneyService hotelRoomMoneyService;

	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	@Autowired
	private HotelRoomPriceService hotelRoomPriceService;

	@Autowired
	private HotelCouponsCashService hotelCouponsCashService;

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private HotelSellerService hotelSellerService;

	@Autowired
	private HotelOrderRecordService hotelOrderRecordService;

	@Autowired
	@Qualifier("wxTransactionService")
	private TransactionService transactionService;

	@Autowired
	private HotelScoreService hotelScoreService;

	@Autowired
	private HotelWxConfigService hotelWxConfigService;

	@Autowired
	private HotelWxTemplateService hotelWxTemplateService;

	@Autowired
	private HotelContactsService hotelContactsService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelOrderEntity> page = this.page(new Query<HotelOrderEntity>().getPage(params), new QueryWrapper<HotelOrderEntity>().eq(null != sellerId, "seller_id", sellerId));
		return new PageUtils(page);
	}

	@Override
	public BuildOrderForm buildOrder(Long userId, Long roomId, Long moneyId, Long contactsId, Long couponId, int roomNum, String checkInDate, String checkOutDate) {
		log.info("构建订单信息--start,userId,{},roomId:{},moneyId:{},checkInDate:{},checkOutDate:{}", userId, roomId, moneyId, checkInDate, checkOutDate);
		BuildOrderForm buildOrderForm = new BuildOrderForm();
		// 总金额
		Double totalAmount = 0.0;
		// 订单明细
		OrderDetail orderDetail = null;
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		// 计算入住天数
		long checkInDay = DateUtil.between(DateUtil.parse(checkInDate), DateUtil.parse(checkOutDate), DateUnit.DAY);
		// 房间信息
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(roomId);
		buildOrderForm.setCheckInDate(checkInDate);
		buildOrderForm.setCheckOutDate(checkOutDate);
		buildOrderForm.setCheckInDay(checkInDay);
		buildOrderForm.setRoomName(hotelRoomEntity.getName());
		buildOrderForm.setRoomNum(roomNum);
		buildOrderForm.setContactsId(contactsId);
		// 查询房价
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(moneyId);
		// 积分换房
		if (null != hotelRoomMoneyEntity.getIntegral() || NumberUtil.isGreater(new BigDecimal(0), hotelRoomMoneyEntity.getIntegral())) {
			buildOrderForm.setPayIntegral(NumberUtil.mul(roomNum, hotelRoomMoneyEntity.getIntegral()));
		}
		// 房型价格是否需要预付
		buildOrderForm.setPrepay(hotelRoomMoneyEntity.getPrepay());
		HotelRoomPriceEntity hotelRoomPriceEntity = null;
		DateTime curentTime = new DateTime(DateUtil.parse(checkInDate));
		// 酒店信息
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelRoomEntity.getSellerId());
		buildOrderForm.setHotelAddress(hotelSellerEntity.getAddress());
		buildOrderForm.setSellerName(hotelSellerEntity.getName());
		for (int i = 0; i < checkInDay; i++) {
			orderDetail = new OrderDetail();
			// 是否有特殊价格
			long thisdateTimes = curentTime.millsecond();// 当天时间戳
			log.info("查询每日房价--start，time:{},roomId:{},moneyId:{},sellerId:{}", thisdateTimes, roomId, moneyId, hotelRoomEntity.getSellerId());
			hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("seller_id", hotelRoomEntity.getSellerId()).eq("money_id", moneyId).eq("room_id", roomId).eq("thisdate", thisdateTimes));
			// 时间后移
			curentTime = DateUtil.offsetDay(curentTime, 1);
			Double amount = 0.0;
			if (null != hotelRoomPriceEntity) {
				log.info("日期:{},存在特殊价格:{}", curentTime, hotelRoomPriceEntity.getMprice());
				// 使用特殊价格--暂时取会员价
				amount = NumberUtil.mul(roomNum, hotelRoomPriceEntity.getMprice().doubleValue());
			} else {
				// 使用原价--暂时取会员价
				amount = NumberUtil.mul(roomNum, hotelRoomMoneyEntity.getPrice().doubleValue());
			}
			totalAmount = NumberUtil.add(totalAmount, amount);
			orderDetail.setAmount(NumberUtil.decimalFormat("0.00", amount));
			orderDetail.setNum(roomNum);
			log.info("查询每日房价--end，result:{}", JSON.toJSONString(orderDetail));
			orderDetails.add(orderDetail);
		}
		// 优惠券信息
		if (null != couponId && couponId > 0) {
			HotelCouponsCashEntity hotelCouponsCashEntity = hotelCouponsCashService.getById(couponId);
			if (NumberUtil.isGreater(new BigDecimal(totalAmount), hotelCouponsCashEntity.getConditions())) {
				buildOrderForm.setDiscountsAcmount(NumberUtil.decimalFormat("0.00", hotelCouponsCashEntity.getCost().doubleValue()));
				totalAmount = NumberUtil.sub(totalAmount.doubleValue(), hotelCouponsCashEntity.getCost().doubleValue());
			}
		}
		buildOrderForm.setTotalAmount(NumberUtil.decimalFormat("0.00", totalAmount));
		BigDecimal totalAmountFen = NumberUtil.mul(totalAmount, new BigDecimal(100));
		buildOrderForm.setTotalAmountFen(totalAmountFen.intValue());
		buildOrderForm.getRecord().addAll(orderDetails);
		buildOrderForm.setContactsId(contactsId);
		// 用户积分 余额
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, hotelRoomEntity.getSellerId()).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		if (null != hotelMemberLevelDetailEntity) {
			buildOrderForm.setMemberBalance(hotelMemberLevelDetailEntity.getBalance());
			buildOrderForm.setMemberIntegral(hotelMemberLevelDetailEntity.getScore());
		}
		log.info("构建订单信息--end,result:{}", JSON.toJSONString(buildOrderForm));
		return buildOrderForm;
	}

	@SneakyThrows
	@Override
	@Transactional
	public WxPayMpOrderResult createOrder(BuildOrderForm buildOrderForm, Long userId) throws WxPayException {
		CreateOrderForm createOrderForm = new CreateOrderForm();
		BuildOrderForm newBuildOrderForm = this.buildOrder(userId, buildOrderForm.getRoomId(), buildOrderForm.getMoneyId(), buildOrderForm.getContactsId(), buildOrderForm.getCouponId(), buildOrderForm.getRoomNum(), buildOrderForm.getCheckInDate(), buildOrderForm.getCheckOutDate());
		BeanUtil.copyProperties(newBuildOrderForm, createOrderForm);
		createOrderForm.setPayMethod(buildOrderForm.getPayMethod());
		createOrderForm.setFormId(buildOrderForm.getFormId());
		createOrderForm.setRoomId(buildOrderForm.getRoomId());
		createOrderForm.setMoneyId(buildOrderForm.getMoneyId());
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(buildOrderForm.getRoomId());
		createOrderForm.setSellerId(hotelRoomEntity.getSellerId());
		createOrderForm.setUserId(userId);
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(buildOrderForm.getMoneyId());
		HotelContactsEntity hotelContactsEntity = hotelContactsService.getById(buildOrderForm.getContactsId());
		createOrderForm.setCheckInPerson(hotelContactsEntity.getName());
		createOrderForm.setMobile(hotelContactsEntity.getMobile());
		HotelOrderEntity hotelOrderEntity = this.createHotelOrder(createOrderForm);
		// 酒店信息
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelRoomEntity.getSellerId());
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		// 商户微信信息
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		String formId = buildOrderForm.getFormId();
		if (PayMethodConstants.BALANCE.equals(buildOrderForm.getPayMethod())) {
			// 扣除余额
			hotelMemberLevelDetailService.balanceTransaction(hotelRoomEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
			// 扣减房量
			hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, -buildOrderForm.getRoomNum());
			// 更新订单
			this.updateOrderStatus2Payed(hotelOrderEntity.getId());
			// 发送预定通知
			sendReserveMessage(createOrderForm, hotelOrderEntity, hotelSellerEntity, hotelMemberEntity, hotelWxConfigEntity, formId);
			// 添加流水
			HotelMemberLevelDetailEntity memberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, hotelOrderEntity.getSellerId()).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
			hotelRechargeService.addConsumptionRecord(-hotelOrderEntity.getTotalCost().intValue(), memberLevelDetailEntity.getLevelId(), userId, "在线预定，" + hotelOrderEntity.getRoomType());
			return null;
		} else if (PayMethodConstants.INTEGRAL.equals(buildOrderForm.getPayMethod())) {
			// 扣除积分
			hotelMemberLevelDetailService.integralTransaction(hotelRoomEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
			// 扣减房量
			hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, -buildOrderForm.getRoomNum());
			// 更新订单
			this.updateOrderStatus2Payed(hotelOrderEntity.getId());
			// 发送预定通知
			sendReserveMessage(createOrderForm, hotelOrderEntity, hotelSellerEntity, hotelMemberEntity, hotelWxConfigEntity, formId);
			// 添加积分流水
			hotelScoreService.transactionScore(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), 2, hotelOrderEntity.getTotalCost().intValue(), "在线预定，" + hotelOrderEntity.getRoomType());
			return null;
		} else if (PayMethodConstants.WX.equals(buildOrderForm.getPayMethod())) {
			log.info("调用微信统一下单--start,userId:{},sellerId:{},params:{}", userId, hotelRoomEntity.getSellerId(), JSON.toJSONString(buildOrderForm));
			WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
			wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
			wxPayUnifiedOrderRequest.setBody(hotelSellerEntity.getName() + "-" + createOrderForm.getRoomName() + "(" + createOrderForm.getCheckInDay() + "晚)");
			wxPayUnifiedOrderRequest.setOutTradeNo(hotelOrderEntity.getOutTradeNo());
			wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
			wxPayUnifiedOrderRequest.setNotifyUrl("https://hotelapi.xqtinfo.cn/pay/" + hotelWxConfigEntity.getAppId() + "/notify/order");
			wxPayUnifiedOrderRequest.setTradeType("JSAPI");
			wxPayUnifiedOrderRequest.setTotalFee(1);
			wxPayUnifiedOrderRequest.setAttach(JSON.toJSONString(new OrderType(OrderTypeConstants.order_room, buildOrderForm.getFormId())));
			wxPayUnifiedOrderRequest.setSpbillCreateIp(buildOrderForm.getIp());
			WxPayMpOrderResult mpOrderResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).createOrder(wxPayUnifiedOrderRequest);
			log.info("调用微信统一下单--start,result:{}", JSON.toJSONString(mpOrderResult));
			hotelOrderEntity.setFormId(mpOrderResult.getPackageValue().substring("prepay_id=".length(), mpOrderResult.getPackageValue().length()));
			baseMapper.updateById(hotelOrderEntity);
			// 不需要预付，先发送订房成功通知
			if (createOrderForm.getPrepay() == 0) {
				sendReserveMessage(createOrderForm, hotelOrderEntity, hotelSellerEntity, hotelMemberEntity, hotelWxConfigEntity, formId);
			}
			return mpOrderResult;
		} else {
			throw new RRException("请选择支付方式");
		}
	}

	public void sendReserveMessage(CreateOrderForm createOrderForm, HotelOrderEntity hotelOrderEntity, HotelSellerEntity hotelSellerEntity, HotelMemberEntity hotelMemberEntity, HotelWxConfigEntity hotelWxConfigEntity, String formId) throws WxErrorException {
		List<WxMaTemplateData> maTemplateDatas = new ArrayList<WxMaTemplateData>();
		maTemplateDatas.add(new WxMaTemplateData("first", "酒店预订成功通知"));
		maTemplateDatas.add(new WxMaTemplateData("keyword1", hotelSellerEntity.getName()));
		maTemplateDatas.add(new WxMaTemplateData("keyword2", DateUtil.format(hotelOrderEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
		maTemplateDatas.add(new WxMaTemplateData("keyword3", DateUtil.formatDate(hotelOrderEntity.getArrivalTime())));
		maTemplateDatas.add(new WxMaTemplateData("keyword4", DateUtil.formatDate(hotelOrderEntity.getDepartureTime())));
		maTemplateDatas.add(new WxMaTemplateData("keyword5", hotelOrderEntity.getName()));
		maTemplateDatas.add(new WxMaTemplateData("keyword6", hotelSellerEntity.getTel()));
		WxMaTemplateMessage maTemplateMessage = new WxMaTemplateMessage(hotelMemberEntity.getOpenid(), "8Gs6-DAfpKktBMZmWJBPVnMzC3_MlP9Sz0ug4JrFqeg", null, formId, maTemplateDatas, null);
		try {
			WxMaConfiguration.getMaService(hotelWxConfigEntity.getAppId()).getMsgService().sendTemplateMsg(maTemplateMessage);
		} catch (Exception e) {
			throw new RRException("发送微信通知失败");
		}
	}

	/**
	 * 创建订单
	 * 
	 * @param createOrderForm
	 * @return
	 */
	@Transactional
	private HotelOrderEntity createHotelOrder(CreateOrderForm createOrderForm) {
		log.info("创建酒店订单--start,params:{}", JSON.toJSONString(createOrderForm));
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(createOrderForm.getSellerId());
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(createOrderForm.getRoomId());
		HotelOrderEntity hotelOrderEntity = new HotelOrderEntity();
		hotelOrderEntity.setSellerId(createOrderForm.getSellerId());
		hotelOrderEntity.setRoomId(createOrderForm.getRoomId());
		hotelOrderEntity.setUserId(createOrderForm.getUserId());
		hotelOrderEntity.setCouponsId(createOrderForm.getCouponId());
		hotelOrderEntity.setMoneyId(createOrderForm.getMoneyId());
		hotelOrderEntity.setOrderNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS") + createOrderForm.getUserId());
		hotelOrderEntity.setSellerName(hotelSellerEntity.getName());
		hotelOrderEntity.setSellerAddress(hotelSellerEntity.getAddress());
		hotelOrderEntity.setCoordinates(createOrderForm.getCoordinates());
		hotelOrderEntity.setArrivalTime(DateUtil.parse(createOrderForm.getCheckInDate()));
		hotelOrderEntity.setDepartureTime(DateUtil.parse(createOrderForm.getCheckOutDate()));
		hotelOrderEntity.setNum(createOrderForm.getRoomNum());
		hotelOrderEntity.setFormId(createOrderForm.getFormId());
		hotelOrderEntity.setDays(Integer.valueOf(String.valueOf(createOrderForm.getCheckInDay())));
		hotelOrderEntity.setRoomType(hotelRoomEntity.getName());
		hotelOrderEntity.setContactsId(createOrderForm.getContactsId());
		hotelOrderEntity.setName(createOrderForm.getCheckInPerson());
		hotelOrderEntity.setOutTradeNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS" + createOrderForm.getUserId()));
		hotelOrderEntity.setStatus(HotelOrderStatus.UN_PAY);
		hotelOrderEntity.setTel(createOrderForm.getMobile());
		hotelOrderEntity.setCreateTime(DateUtil.date());
		hotelOrderEntity.setEnabled(1);
		hotelOrderEntity.setPayMethod(createOrderForm.getPayMethod());
		hotelOrderEntity.setTotalCost(BigDecimal.valueOf(Long.valueOf(createOrderForm.getTotalAmountFen())).divide(new BigDecimal(100)));
		hotelOrderEntity.setTotalIntegral(createOrderForm.getPayIntegral());
		if (null != createOrderForm.getCouponId() && 0L != createOrderForm.getCouponId()) {
//			HotelCouponsCashEntity hotelCouponsCashEntity = hotelCouponsCashService.getById(couponId);
//			if (NumberUtil.isGreater(new BigDecimal(totalAmount), hotelCouponsCashEntity.getConditions())) {
//				buildOrderForm.setDiscountsAcmount(NumberUtil.decimalFormat("0.00", hotelCouponsCashEntity.getCost().doubleValue()));
//				totalAmount = NumberUtil.sub(totalAmount.doubleValue(), hotelCouponsCashEntity.getCost().doubleValue());
//			}
		}
		this.save(hotelOrderEntity);
		// 保存订单明细
		this.createOrderRecord(createOrderForm, hotelOrderEntity.getId());
		// 扣减房量 TODO
		log.info("创建酒店订单--end,result,orderId:{}", hotelOrderEntity.getId());
		return hotelOrderEntity;
	}

	/**
	 * 创建订单明细
	 * 
	 * @param createOrderForm
	 * @param orderId
	 */
	@Transactional
	private void createOrderRecord(CreateOrderForm createOrderForm, Long orderId) {
		log.info("创建订单明细--start,orderId:{},params:{}", orderId, JSON.toJSONString(createOrderForm));
		// 查询房价
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(createOrderForm.getMoneyId());
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(createOrderForm.getRoomId());
		HotelRoomPriceEntity hotelRoomPriceEntity = null;
		DateTime curentTime = new DateTime(DateUtil.parse(createOrderForm.getCheckInDate()));
		HotelOrderRecordEntity hotelOrderRecordEntity = null;
		List<HotelOrderRecordEntity> orderRecordEntities = new ArrayList<HotelOrderRecordEntity>();
		for (int i = 0; i < createOrderForm.getCheckInDay(); i++) {
			// 是否有特殊价格
			long thisdateTimes = curentTime.millsecond();// 当天时间戳
			log.info("创建订单明细--price，time:{},roomId:{},moneyId:{},sellerId:{}", thisdateTimes, createOrderForm.getRoomId(), createOrderForm.getMoneyId(), createOrderForm.getSellerId());
			hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("seller_id", createOrderForm.getSellerId()).eq("money_id", createOrderForm.getMoneyId()).eq("room_id", createOrderForm.getRoomId()).eq("thisdate", thisdateTimes));
			// 时间后移
			curentTime = DateUtil.offsetDay(curentTime, 1);
			Double amount = 0.0;
			if (null != hotelRoomPriceEntity) {
				log.info("创建订单明细--日期:{},存在特殊价格:{}", curentTime, hotelRoomPriceEntity.getMprice());
				// 使用特殊价格--暂时取会员价
				amount = NumberUtil.mul(createOrderForm.getRoomNum(), hotelRoomPriceEntity.getMprice().doubleValue());
			} else {
				// 使用原价--暂时取会员价
				amount = NumberUtil.mul(createOrderForm.getRoomNum(), hotelRoomMoneyEntity.getPrice().doubleValue());
			}
			hotelOrderRecordEntity = new HotelOrderRecordEntity();
			hotelOrderRecordEntity.setAmount(new BigDecimal(amount));
			hotelOrderRecordEntity.setCreateTime(DateUtil.date());
			hotelOrderRecordEntity.setMoneyId(createOrderForm.getMoneyId());
			hotelOrderRecordEntity.setOrderId(orderId);
			if (null != hotelRoomPriceEntity) {
				hotelOrderRecordEntity.setPriceId(hotelRoomPriceEntity.getId());
			}
			hotelOrderRecordEntity.setSellerId(createOrderForm.getSellerId());
			hotelOrderRecordEntity.setRoomType(hotelRoomEntity.getName() + "-" + hotelRoomMoneyEntity.getName());
			orderRecordEntities.add(hotelOrderRecordEntity);
		}
		hotelOrderRecordService.saveBatch(orderRecordEntities);
		log.info("创建订单明细--end,result:{}", JSON.toJSONString(orderRecordEntities));
	}

	@Override
	public Page<HotelOrderVo> userOrderList(Long userId, Integer orderStatus, int page, int limie) {
		log.info("获取用户订单--start,userId:{},orderStatus:{}", userId, orderStatus);
		QueryWrapper<HotelOrderEntity> queryWrapper = new QueryWrapper<HotelOrderEntity>();
		if (null != orderStatus) {
			queryWrapper.eq("status", orderStatus);
		}
		queryWrapper.eq("enabled", 1);
		queryWrapper.eq("user_id", userId);
		queryWrapper.orderByDesc("create_time");
		Map<String, Object> pageMap = new HashMap<String, Object>();
		IPage<HotelOrderEntity> pageResult = this.page(new Query<HotelOrderEntity>().getPage(pageMap), queryWrapper);
		List<HotelOrderEntity> hotelOrderEntities = pageResult.getRecords();
		List<HotelOrderVo> hotelOrderVos = new ArrayList<HotelOrderVo>();
		HotelOrderVo hotelOrderVo = null;
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			hotelOrderVo = new HotelOrderVo();
			BeanUtil.copyProperties(hotelOrderEntity, hotelOrderVo);
			hotelOrderVo.setTotalCost(NumberUtil.decimalFormat("0.00", hotelOrderEntity.getTotalCost().doubleValue()));
			hotelOrderVos.add(hotelOrderVo);
		}
		log.info("获取用户订单--end,record:{},total,size:{},current:{}", JSON.toJSONString(hotelOrderVos), pageResult.getTotal(), pageResult.getSize(), pageResult.getCurrent());
		Page<HotelOrderVo> returnPage = new Page<HotelOrderVo>();
		returnPage.setTotal(pageResult.getTotal());
		returnPage.setCurrent(pageResult.getCurrent());
		returnPage.setRecords(hotelOrderVos);
		return returnPage;
	}

	@Override
	public HotelOrderVo orderDetail(Long userId, Long orderId) {
		log.info("查询订单详情--start,sellerId:{},userId:{},", userId, orderId);
		HotelOrderEntity hotelOrderEntity = this.getOne(new QueryWrapper<HotelOrderEntity>().eq("id", orderId).eq("user_id", userId));
		if (null == hotelOrderEntity) {
			log.error("查询订单详情--参数错误，未找到订单信息,userId:{},orderId:{}", userId, orderId);
			throw new RRException("参数错误，未找到订单信息");
		}
		HotelOrderVo hotelOrderVo = new HotelOrderVo();
		BeanUtil.copyProperties(hotelOrderEntity, hotelOrderVo);
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
		hotelOrderVo.setSellerTel(hotelSellerEntity.getTel());
		hotelOrderVo.setCoordinates(hotelSellerEntity.getCoordinates());
		hotelOrderVo.setTotalCost(NumberUtil.decimalFormat("0.00", hotelOrderEntity.getTotalCost().doubleValue()));
		log.info("查询订单详情--end,result:{}", JSON.toJSONString(hotelOrderVo));
		return hotelOrderVo;
	}

	@SneakyThrows
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelOrder(Long userId, Long orderId, String formId) {
		log.info("取消订单--start,userId:{},orderId:{}", userId, orderId);
		HotelOrderEntity hotelOrderEntity = this.getOne(new QueryWrapper<HotelOrderEntity>().eq("id", orderId).eq("user_id", userId));
		if (null == hotelOrderEntity) {
			log.error("取消订单--参数错误，未找到订单信息,userId:{},orderId:{}", userId, orderId);
			throw new RRException("参数错误，未找到订单信息");
		}
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(hotelOrderEntity.getUserId());
		// 判断订单状态
		if (hotelOrderEntity.getStatus().intValue() == HotelOrderStatus.PAYED) {
			if (hotelOrderEntity.getPayMethod().equals(PayMethodConstants.WX)) {
				log.info("取消订单--订单已支付，执行退款");
				// 已经在线付款，需要判断是否满足取消规则 TODO
				Map<String, Object> refundParams = new HashMap<String, Object>();
				HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
				refundParams.put("appId", hotelWxConfigEntity.getAppId());
				refundParams.put("outTradeNo", hotelOrderEntity.getOutTradeNo());
				refundParams.put("totalFee", 1);
				refundParams.put("refundFee", 1);
				transactionService.refund(refundParams);
				hotelOrderEntity.setStatus(HotelOrderStatus.APPLY_REFUND);
			}
			if (hotelOrderEntity.getPayMethod().equals(PayMethodConstants.BALANCE)) {
				hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
				// 恢复房量
				HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
				HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
				hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, hotelOrderEntity.getNum());
				// 恢复余额
				hotelMemberLevelDetailService.addBalance(hotelRoomEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
				// 添加流水
				HotelMemberLevelDetailEntity memberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, hotelOrderEntity.getUserId()).eq(HotelMemberLevelDetailEntity::getSellerId, hotelOrderEntity.getSellerId()));
				hotelRechargeService.addConsumptionRecord(hotelOrderEntity.getTotalCost().intValue(), memberLevelDetailEntity.getLevelId(), userId, "取消订房，" + hotelOrderEntity.getRoomType());
			}
			if (hotelOrderEntity.getPayMethod().equals(PayMethodConstants.INTEGRAL)) {
				hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
				// 恢复房量
				HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
				HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
				hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, hotelOrderEntity.getNum());
				// 恢复积分
				hotelMemberLevelDetailService.addIntegral(hotelOrderEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
				// 添加积分流水
				hotelScoreService.transactionScore(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), 2, hotelOrderEntity.getTotalCost().intValue(), "取消订房，" + hotelOrderEntity.getRoomType());
			}
		}
		if (hotelOrderEntity.getStatus().intValue() == HotelOrderStatus.UN_PAY) {
			log.info("取消订单--订单未支付，直接取消");
			hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
		}
		this.updateById(hotelOrderEntity);
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		// 发送取消订房通知
		List<WxMaTemplateData> maTemplateDatas = new ArrayList<WxMaTemplateData>();
		maTemplateDatas.add(new WxMaTemplateData("first", "订房取消通知"));
		maTemplateDatas.add(new WxMaTemplateData("keyword1", hotelSellerEntity.getName()));
		maTemplateDatas.add(new WxMaTemplateData("keyword2", DateUtil.format(hotelOrderEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
		maTemplateDatas.add(new WxMaTemplateData("keyword3", DateUtil.formatDate(hotelOrderEntity.getArrivalTime())));
		maTemplateDatas.add(new WxMaTemplateData("keyword4", DateUtil.formatDate(hotelOrderEntity.getDepartureTime())));
		maTemplateDatas.add(new WxMaTemplateData("keyword5", hotelOrderEntity.getRoomType()));
		WxMaTemplateMessage maTemplateMessage = new WxMaTemplateMessage(hotelMemberEntity.getOpenid(), "Fd9nEk2KlaHR80YSIYaVNMSZqrR3vG2x1HnhdfcxHVo", null, formId, maTemplateDatas, null);
		WxMaConfiguration.getMaService(hotelWxConfigEntity.getAppId()).getMsgService().sendTemplateMsg(maTemplateMessage);
		log.info("取消订单--end,result:success");
	}

	@Override
	public void updateOrderStatus2Payed(String outTradeNo) {
		log.info("更新订单为支付成功--start,outTradeNo:{}", outTradeNo);
		// 这里是否需要增加乐观锁，防止重复更新 TODO

		try {
			HotelOrderEntity hotelOrderEntity = this.getOne(new QueryWrapper<HotelOrderEntity>().eq("out_trade_no", outTradeNo));
			if (null == hotelOrderEntity) {
				log.error("未找到订单信息,outTradeNo:{}", outTradeNo);
				return;
			}
			// 商户微信信息
			HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
			// SUCCESS—支付成功,REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败)
			WxPayOrderQueryResult payOrderQueryResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).queryOrder(null, outTradeNo);
			String wxOrderStatus = payOrderQueryResult.getTradeState();
			if (!"SUCCESS".equalsIgnoreCase(wxOrderStatus)) {
				log.warn("注意，支付回调，微信订单状态异常，wxOrderStatus:{}", wxOrderStatus);
				return;
			}

			hotelOrderEntity.setStatus(HotelOrderStatus.PAYED);
			this.updateById(hotelOrderEntity);
			// 发送模板支付成功通知 TODO 目前采用异步线程，后期要改为消息队列
			// 增加积分
			hotelScoreService.transactionScore(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), 2, hotelOrderEntity.getTotalCost().intValue(), "订单支付成功");
		} catch (WxPayException e) {
			log.error("search wx order error,outTradeNo:{}", outTradeNo);
			throw new RRException("查询微信支付订单异常");
		}
		log.info("更新订单为支付成功--end");
	}

	@Override
	public WxPayMpOrderResult payOrder(Long userId, Long orderId, String ip) throws WxPayException {
		HotelOrderEntity hotelOrderEntity = this.getById(orderId);
		// 商户微信信息
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
		hotelOrderEntity.setOrderNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS" + userId));
		// 商户微信信息
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setBody(hotelOrderEntity.getSellerName() + "-" + hotelRoomEntity.getName() + "(" + hotelOrderEntity.getDays() + "晚)");
		wxPayUnifiedOrderRequest.setOutTradeNo(hotelOrderEntity.getOutTradeNo());
		wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
		wxPayUnifiedOrderRequest.setNotifyUrl("https://hotelapi.xqtinfo.cn/pay/" + hotelWxConfigEntity.getAppId() + "/notify/order");
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setTotalFee(1);
		wxPayUnifiedOrderRequest.setAttach(JSON.toJSONString(new OrderType(OrderTypeConstants.order_room)));
		wxPayUnifiedOrderRequest.setSpbillCreateIp(ip);
		WxPayMpOrderResult mpOrderResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).createOrder(wxPayUnifiedOrderRequest);
		this.updateById(hotelOrderEntity);
		return mpOrderResult;
	}

	@Override
	@Transactional
	public void deleteOrder(Long userId, Long orderId) {
		log.info("删除订单--start,userId:{},orderId:{}", userId, orderId);
		HotelOrderEntity hotelOrderEntity = this.getById(orderId);
		if (hotelOrderEntity.getUserId().intValue() != userId.intValue()) {
			log.error("删除订单， 当前订单与用户ID不匹配！！！");
			throw new RRException("非法操作");
		}
		hotelOrderEntity.setEnabled(-1);
		this.updateById(hotelOrderEntity);
		log.info("删除订单--success");
	}

	@Override
	@Transactional
	public void updateOrder2Cancel() {
		log.info("自动取消订单--start");
		Map<String, Object> params = new HashMap<String, Object>();
		// 20分钟自动取消
		IPage<HotelOrderEntity> page = this.page(new Query<HotelOrderEntity>().getPage(params), new QueryWrapper<HotelOrderEntity>().eq("status", HotelOrderStatus.UN_PAY).le("create_time", DateUtil.offset(DateUtil.date(), DateField.MINUTE, -30)));
		List<HotelOrderEntity> hotelOrderEntities = page.getRecords();
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
		}
		if (CollectionUtil.isNotEmpty(hotelOrderEntities)) {
			this.updateBatchById(hotelOrderEntities);
		}
		// 发送取消订单通知
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
				for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
					hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
					if (null != hotelWxConfigEntity) {
						mpService = WxMpConfiguration.getMpServices().get(hotelWxConfigEntity.getAppId());
						data = new ArrayList<>();
						data.add(new WxMpTemplateData("first", "您好，您的订单已取消"));
						data.add(new WxMpTemplateData("keyword1", hotelOrderEntity.getSellerName()));
						data.add(new WxMpTemplateData("keyword2", hotelOrderEntity.getRoomType()));
						data.add(new WxMpTemplateData("keyword3", hotelOrderEntity.getName()));
						data.add(new WxMpTemplateData("keyword4", hotelOrderEntity.getTotalCost().toString()));
						data.add(new WxMpTemplateData("keyword5", DateUtil.format(hotelOrderEntity.getArrivalTime(), "yyyy-MM-dd")));
						data.add(new WxMpTemplateData("keyword6", DateUtil.format(hotelOrderEntity.getDepartureTime(), "yyyy-MM-dd")));
						data.add(new WxMpTemplateData("remark", "您的订单已取消，期待你的下次预定。"));
						hotelMemberEntity = hotelMemberService.getById(hotelOrderEntity.getUserId());
						hotelWxTemplateEntity = hotelWxTemplateService.getOne(new QueryWrapper<HotelWxTemplateEntity>().eq("seller_id", hotelOrderEntity.getSellerId()).eq("type", HotelWxMsgTemplate.ORDER_ROOM_CANCEL));
						templateMessage = new WxMpTemplateMessage(hotelMemberEntity.getOpenid(), hotelWxTemplateEntity.getTemplateId(), null, null, data);
						try {
							String result = mpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
							log.info("发送取消订单微信模板消息：result：{}", result);
						} catch (WxErrorException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});
		log.info("自动取消订单--end");
	}

	@Override
	public void completeOrder() {
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateOrderStatus2Payed(Long orderId) {
		HotelOrderEntity hotelOrderEntity = baseMapper.selectById(orderId);
		hotelOrderEntity.setStatus(HotelOrderStatus.PAYED);
		baseMapper.updateById(hotelOrderEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateOrderStatus2Refunded(String outTradeNo) {
		HotelOrderEntity hotelOrderEntity = baseMapper.selectOne(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getOutTradeNo, outTradeNo));
		hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
		// 恢复房量
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, hotelOrderEntity.getNum());
		baseMapper.updateById(hotelOrderEntity);
	}
}