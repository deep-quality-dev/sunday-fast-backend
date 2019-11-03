package io.renren.modules.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.CommonConstant;
import io.renren.modules.constants.HotelOrderStatus;
import io.renren.modules.constants.HotelWxMsgTemplate;
import io.renren.modules.constants.OrderTypeConstants;
import io.renren.modules.constants.PayMethodConstants;
import io.renren.modules.hotel.config.WxMaConfiguration;
import io.renren.modules.hotel.config.WxMpConfiguration;
import io.renren.modules.hotel.config.WxPayConfiguration;
import io.renren.modules.hotel.dao.HotelInvoiceDao;
import io.renren.modules.hotel.dao.HotelOrderDao;
import io.renren.modules.hotel.entity.HotelContactsEntity;
import io.renren.modules.hotel.entity.HotelCouponsBreakfastEntity;
import io.renren.modules.hotel.entity.HotelCouponsCashEntity;
import io.renren.modules.hotel.entity.HotelCouponsEntity;
import io.renren.modules.hotel.entity.HotelInvoiceEntity;
import io.renren.modules.hotel.entity.HotelMemberCouponsEntity;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.entity.HotelOrderNotificationEntity;
import io.renren.modules.hotel.entity.HotelOrderRecordEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingDateEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingEntity;
import io.renren.modules.hotel.entity.HotelOrderSettingRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomNumEntity;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.entity.HotelWxConfigEntity;
import io.renren.modules.hotel.entity.HotelWxTemplateEntity;
import io.renren.modules.hotel.enums.EnumSmsChannelTemplate;
import io.renren.modules.hotel.form.BuildOrderForm;
import io.renren.modules.hotel.form.CreateOrderForm;
import io.renren.modules.hotel.handler.message.SmsMessageHandler;
import io.renren.modules.hotel.handler.message.template.MobileMsgTemplate;
import io.renren.modules.hotel.service.HotelContactsService;
import io.renren.modules.hotel.service.HotelCouponsBreakfastService;
import io.renren.modules.hotel.service.HotelCouponsCashService;
import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.service.HotelMemberCouponsService;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelOrderNotificationService;
import io.renren.modules.hotel.service.HotelOrderRecordService;
import io.renren.modules.hotel.service.HotelOrderService;
import io.renren.modules.hotel.service.HotelOrderSettingDateService;
import io.renren.modules.hotel.service.HotelOrderSettingRoomService;
import io.renren.modules.hotel.service.HotelOrderSettingService;
import io.renren.modules.hotel.service.HotelRechargeService;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomNumService;
import io.renren.modules.hotel.service.HotelRoomPriceService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.HotelWxConfigService;
import io.renren.modules.hotel.service.HotelWxTemplateService;
import io.renren.modules.hotel.service.TransactionService;
import io.renren.modules.hotel.vo.HotelOrderVo;
import io.renren.modules.hotel.vo.OrderDetail;
import io.renren.modules.hotel.ws.NotificationServer;
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
	private HotelRoomNumService hotelRoomNumService;

	@Autowired
	private HotelCouponsCashService hotelCouponsCashService;

	@Autowired
	private HotelCouponsBreakfastService hotelCouponsBreakfastService;

	@Autowired
	private HotelCouponsService hotelCouponsService;

	@Autowired
	private HotelMemberCouponsService hotelMemberCouponsService;

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

	@Autowired
	private HotelInvoiceDao hotelInvoiceDao;

	@Autowired
	private HotelOrderNotificationService hotelOrderNotificationService;

	@Autowired
	private HotelOrderSettingService hotelOrderSettingService;

	@Autowired
	private HotelOrderSettingRoomService hotelOrderSettingRoomService;

	@Autowired
	private HotelOrderSettingDateService hotelOrderSettingDateService;

	@Autowired
	private Map<String, SmsMessageHandler> messageHandlerMap;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Object sellerId = params.get("seller_id");
		IPage<HotelOrderEntity> page = this.page(new Query<HotelOrderEntity>().getPage(params), new QueryWrapper<HotelOrderEntity>().eq(null != sellerId, "seller_id", sellerId).orderByDesc("create_time"));
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
		if (null != hotelRoomMoneyEntity.getIntegral() && NumberUtil.isGreater(new BigDecimal(0), hotelRoomMoneyEntity.getIntegral())) {
			buildOrderForm.setPayIntegral(NumberUtil.mul(roomNum, hotelRoomMoneyEntity.getIntegral()));
		}
		HotelRoomNumEntity hotelRoomNumEntity = null;
		// 房型价格是否需要预付
		buildOrderForm.setPrepay(hotelRoomMoneyEntity.getPrepay());
		HotelRoomPriceEntity hotelRoomPriceEntity = null;
		DateTime curentTime = new DateTime(DateUtil.parse(checkInDate));
		// 酒店信息
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelRoomEntity.getSellerId());
		buildOrderForm.setHotelAddress(hotelSellerEntity.getAddress());
		buildOrderForm.setRoomId(roomId);
		buildOrderForm.setSellerName(hotelSellerEntity.getName());
		for (int i = 0; i < checkInDay; i++) {
			orderDetail = new OrderDetail();
			// 是否有特殊价格
			long thisdateTimes = curentTime.getTime();// 当天时间
			log.info("查询每日房价--start，time:{},roomId:{},moneyId:{},sellerId:{}", thisdateTimes, roomId, moneyId, hotelRoomEntity.getSellerId());
			hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("seller_id", hotelRoomEntity.getSellerId()).eq("money_id", moneyId).eq("room_id", roomId).eq("thisdate", thisdateTimes));
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
			orderDetail.setDate(DateUtil.format(curentTime, "yyyy-MM-dd"));
			log.info("查询每日房价--end，result:{}", JSON.toJSONString(orderDetail));
			orderDetails.add(orderDetail);
			hotelRoomNumEntity = hotelRoomNumService.getOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getDateday, curentTime).eq(HotelRoomNumEntity::getMoneyId, hotelRoomMoneyEntity.getId()));
			if (null == hotelRoomNumEntity) {
				if (hotelRoomMoneyEntity.getNum() < roomNum) {
					throw new RRException("房量不足");
				}
			} else {
				if (hotelRoomNumEntity.getNums() < roomNum) {
					throw new RRException("房量不足");
				}
			}
			// 时间后移
			curentTime = DateUtil.offsetDay(curentTime, 1);
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
		log.info("构建订单信息--end,result:{}", JSON.toJSONString(buildOrderForm));
		return buildOrderForm;
	}

	@SneakyThrows
	@Override
	@Transactional
	public Long createOrder(BuildOrderForm buildOrderForm, Long userId) throws WxPayException {
		CreateOrderForm createOrderForm = new CreateOrderForm();
		BuildOrderForm newBuildOrderForm = this.buildOrder(userId, buildOrderForm.getRoomId(), buildOrderForm.getMoneyId(), buildOrderForm.getContactsId(), buildOrderForm.getCouponId(), buildOrderForm.getRoomNum(), buildOrderForm.getCheckInDate(), buildOrderForm.getCheckOutDate());
		BeanUtil.copyProperties(newBuildOrderForm, createOrderForm);
		createOrderForm.setDdTime(buildOrderForm.getDdTime());
		createOrderForm.setBreakCouponId(buildOrderForm.getBreakCouponId());
		createOrderForm.setFreeRoomCouponId(buildOrderForm.getFreeRoomCouponId());
		createOrderForm.setRemark(buildOrderForm.getRemark());
		createOrderForm.setPayMethod(buildOrderForm.getPayMethod());
		createOrderForm.setFormId(buildOrderForm.getFormId());
		createOrderForm.setRoomId(buildOrderForm.getRoomId());
		createOrderForm.setInvoiceId(buildOrderForm.getInvoiceId());
		createOrderForm.setMoneyId(buildOrderForm.getMoneyId());
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(buildOrderForm.getRoomId());
		createOrderForm.setSellerId(hotelRoomEntity.getSellerId());
		createOrderForm.setUserId(userId);
		HotelContactsEntity hotelContactsEntity = hotelContactsService.getById(buildOrderForm.getContactsId());
		createOrderForm.setCheckInPerson(hotelContactsEntity.getName());
		createOrderForm.setMobile(hotelContactsEntity.getMobile());
		createOrderForm.setCouponId(buildOrderForm.getCouponId());
		HotelOrderEntity hotelOrderEntity = this.createHotelOrder(createOrderForm);
		// 酒店信息
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelRoomEntity.getSellerId());
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		// 商户微信信息
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		String formId = buildOrderForm.getFormId();
		// 不需要预付，先发送订房成功通知
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(createOrderForm.getMoneyId());
		if (hotelRoomMoneyEntity.getPrepay() == 0) {
			sendReserveMessage(hotelOrderEntity, hotelSellerEntity, hotelMemberEntity, hotelWxConfigEntity, formId);
			// 商家订单通知
			sendSellerNotiftion(hotelOrderEntity);
		}
		return hotelOrderEntity.getId();
	}

	public void sendSellerNotiftion(HotelOrderEntity hotelOrderEntity) {
		ThreadUtil.execute(new Runnable() {
			@Override
			@SneakyThrows
			public void run() {
				// 订单通知
				HotelOrderNotificationEntity hotelOrderNotificationEntity = hotelOrderNotificationService.getOne(Wrappers.<HotelOrderNotificationEntity>lambdaQuery().eq(HotelOrderNotificationEntity::getSellerId, hotelOrderEntity.getSellerId()));
				if (null != hotelOrderNotificationEntity && hotelOrderEntity.getStatus().intValue() == HotelOrderStatus.WAIT_AFFIRM) {
					// 发送短信
					if (hotelOrderNotificationEntity.getSmsSwitch() == 1 && StrUtil.isNotEmpty(hotelOrderNotificationEntity.getMobile())) {
						List<String> mobiles = Arrays.asList(hotelOrderNotificationEntity.getMobile().split(","));
						JSONObject contextJson = new JSONObject();
						contextJson.put("jdname", hotelOrderEntity.getSellerName());
						contextJson.put("product", hotelOrderEntity.getRoomType());
						// TODO 组装数据采用MQ发送
						for (String mobile : mobiles) {
							MobileMsgTemplate mobileMsgTemplate = new MobileMsgTemplate(mobile, contextJson.toJSONString(), CommonConstant.ALIYUN_SMS, EnumSmsChannelTemplate.SELLER_RESERVE_SUCCESS_CHANNEL.getSignName(), EnumSmsChannelTemplate.SELLER_RESERVE_SUCCESS_CHANNEL.getTemplate());
							String channel = mobileMsgTemplate.getChannel();
							SmsMessageHandler messageHandler = messageHandlerMap.get(channel);
							if (messageHandler != null) {
								messageHandler.execute(mobileMsgTemplate);
							} else {
								log.error("没有找到指定的路由通道，不进行发送处理完毕！");
							}
						}

					}
					// 发送邮件
					if (hotelOrderNotificationEntity.getEmailSwitch() == 1 && StrUtil.isNotEmpty(hotelOrderNotificationEntity.getEmail())) {
						List<String> emails = Arrays.asList(hotelOrderNotificationEntity.getEmail().split(","));
						String emailContent = String.format("%s ，您有新的%s订单，请及时处理！", hotelOrderEntity.getSellerName(), hotelOrderEntity.getRoomType());
						MailAccount account = new MailAccount();
						account.setHost("smtp.qq.com");
						account.setPort(25);
						account.setAuth(true);
						account.setFrom("1137484056@qq.com");
						account.setUser("1137484056@qq.com");
						account.setPass("magxdhevrdzdgjjh");
						MailUtil.send(account, emails, "房间预定提醒", emailContent, false);
					}
					// ws通知
					if (hotelOrderNotificationEntity.getPcSwitch() == 1) {
						HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
						String emailContent = String.format("%s ，您有新的%s订单，请及时处理！", hotelOrderEntity.getSellerName(), hotelOrderEntity.getRoomType());
						NotificationServer.sendInfo(emailContent, hotelSellerEntity.getUserId().toString());
					}
				}

			}
		});
	}

	public void sendReserveMessage(HotelOrderEntity hotelOrderEntity, HotelSellerEntity hotelSellerEntity, HotelMemberEntity hotelMemberEntity, HotelWxConfigEntity hotelWxConfigEntity, String formId) throws WxErrorException {
		ThreadUtil.execute(new Runnable() {
			@Override
			public void run() {
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
		});
	}

	/**
	 * 创建订单
	 * 
	 * @param createOrderForm
	 * @return
	 */
	@Transactional
	public HotelOrderEntity createHotelOrder(CreateOrderForm createOrderForm) {
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
		hotelOrderEntity.setDdTime(createOrderForm.getDdTime());
		hotelOrderEntity.setRemark(createOrderForm.getRemark());
		hotelOrderEntity.setArrivalTime(DateUtil.parse(createOrderForm.getCheckInDate()));
		hotelOrderEntity.setDepartureTime(DateUtil.parse(createOrderForm.getCheckOutDate()));
		hotelOrderEntity.setNum(createOrderForm.getRoomNum());
		hotelOrderEntity.setFormId(createOrderForm.getFormId());
		hotelOrderEntity.setDays(Integer.valueOf(String.valueOf(createOrderForm.getCheckInDay())));
		hotelOrderEntity.setRoomType(hotelRoomEntity.getName());
		hotelOrderEntity.setContactsId(createOrderForm.getContactsId());
		hotelOrderEntity.setName(createOrderForm.getCheckInPerson());
		hotelOrderEntity.setOutTradeNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS" + createOrderForm.getUserId()));
		hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_AFFIRM);
		hotelOrderEntity.setInvoiceId(createOrderForm.getInvoiceId());
		hotelOrderEntity.setTel(createOrderForm.getMobile());
		hotelOrderEntity.setCreateTime(DateUtil.date());
		hotelOrderEntity.setEnabled(1);
		hotelOrderEntity.setPayMethod(createOrderForm.getPayMethod());
		hotelOrderEntity.setTotalCost(BigDecimal.valueOf(Long.valueOf(createOrderForm.getTotalAmountFen())).divide(new BigDecimal(100)));
		hotelOrderEntity.setTotalIntegral(createOrderForm.getPayIntegral());
		// 优惠券信息
		if (null != createOrderForm.getCouponId() && 0L != createOrderForm.getCouponId()) {
			HotelCouponsCashEntity hotelCouponsCashEntity = hotelCouponsCashService.getById(createOrderForm.getCouponId());
			if (NumberUtil.isGreater(hotelOrderEntity.getTotalCost(), hotelCouponsCashEntity.getConditions())) {
				hotelOrderEntity.setTotalCost(NumberUtil.sub(hotelOrderEntity.getTotalCost(), hotelCouponsCashEntity.getConditions()));
				hotelOrderEntity.setYhqCost(hotelCouponsCashEntity.getConditions());
				HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, createOrderForm.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 2).eq(HotelMemberCouponsEntity::getCouponsId, createOrderForm.getCouponId()));
				if (null == hotelMemberCouponsEntity) {
					throw new RRException("用户优惠券不存在");
				}
				hotelMemberCouponsEntity.setState(-1);
				hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
			}
		}
		// 早餐券
		if (null != createOrderForm.getBreakCouponId() && 0L != createOrderForm.getBreakCouponId()) {
			HotelCouponsBreakfastEntity couponsBreakfastEntity = hotelCouponsBreakfastService.getById(createOrderForm.getBreakCouponId());
			if (null == couponsBreakfastEntity) {
				throw new RRException("早餐券不存在");
			}
			if (DateUtil.parse(couponsBreakfastEntity.getEndTime()).getTime() < DateUtil.date().getTime()) {
				throw new RRException("早餐券已过期");
			}
			hotelOrderEntity.setBreakCouponId(createOrderForm.getBreakCouponId());
			HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, createOrderForm.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 3).eq(HotelMemberCouponsEntity::getCouponsId, createOrderForm.getBreakCouponId()));
			if (null == hotelMemberCouponsEntity) {
				throw new RRException("用户优惠券不存在");
			}
			hotelMemberCouponsEntity.setState(-1);
			hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
		}
		// 免房券
		if (null != createOrderForm.getFreeRoomCouponId() && 0L != createOrderForm.getFreeRoomCouponId()) {
			// 开启自动接单
			hotelOrderEntity.setStatus(HotelOrderStatus.CHECK_IN);
			// 订单需要预付
			if (createOrderForm.getPrepay() == 1) {
				hotelOrderEntity.setStatus(HotelOrderStatus.UN_PAY);
			}
			HotelCouponsEntity couponsEntity = hotelCouponsService.getById(createOrderForm.getFreeRoomCouponId());
			if (null == couponsEntity) {
				throw new RRException("免房券不存在");
			}
			if (couponsEntity.getEndTime().getTime() < DateUtil.date().getTime()) {
				throw new RRException("免房券已过期");
			}
			if (createOrderForm.getRoomNum() > 1 || Integer.valueOf(String.valueOf(createOrderForm.getCheckInDay())) > 1) {
				throw new RRException("入住天数，或房间数只能为1（天/间）");
			}
			hotelOrderEntity.setFreeRoomCouponId(createOrderForm.getFreeRoomCouponId());
			HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, createOrderForm.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 1).eq(HotelMemberCouponsEntity::getCouponsId, createOrderForm.getFreeRoomCouponId()));
			if (null == hotelMemberCouponsEntity) {
				throw new RRException("用户优惠券不存在");
			}
			hotelMemberCouponsEntity.setState(-1);
			hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
		}
		// 保存订单
		this.save(hotelOrderEntity);
		//
		this.updateOrderStatusWithOrderSetting(hotelOrderEntity);
		// 保存订单明细
		this.createOrderRecord(createOrderForm, hotelOrderEntity.getId());
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
		HotelRoomNumEntity hotelRoomNumEntity = null;
		DateTime curentTime = new DateTime(DateUtil.parse(createOrderForm.getCheckInDate()));
		HotelOrderRecordEntity hotelOrderRecordEntity = null;
		List<HotelOrderRecordEntity> orderRecordEntities = new ArrayList<HotelOrderRecordEntity>();

		for (int i = 0; i < createOrderForm.getCheckInDay(); i++) {
			// 是否有特殊价格
			long thisdateTimes = curentTime.millsecond();// 当天时间戳
			log.info("创建订单明细--price，time:{},roomId:{},moneyId:{},sellerId:{}", thisdateTimes, createOrderForm.getRoomId(), createOrderForm.getMoneyId(), createOrderForm.getSellerId());
			hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("seller_id", createOrderForm.getSellerId()).eq("money_id", createOrderForm.getMoneyId()).eq("room_id", createOrderForm.getRoomId()).eq("thisdate", thisdateTimes));
			Double amount = 0.0;
			if (null != hotelRoomPriceEntity) {
				log.info("创建订单明细--日期:{},存在特殊价格:{}", curentTime, hotelRoomPriceEntity.getMprice());
				// 使用特殊价格--暂时取会员价
				amount = NumberUtil.mul(createOrderForm.getRoomNum(), hotelRoomPriceEntity.getMprice().doubleValue());
			} else {
				// 使用原价--暂时取会员价
				amount = NumberUtil.mul(createOrderForm.getRoomNum(), hotelRoomMoneyEntity.getPrice().doubleValue());
			}
			// 查询每天的房量，并且创建记录
			hotelRoomNumEntity = hotelRoomNumService.getOne(Wrappers.<HotelRoomNumEntity>lambdaQuery().eq(HotelRoomNumEntity::getDateday, curentTime.getTime()).eq(HotelRoomNumEntity::getMoneyId, hotelRoomMoneyEntity.getId()));
			if (null == hotelRoomNumEntity) {
				hotelRoomNumEntity = new HotelRoomNumEntity();
				hotelRoomNumEntity.setDateday(curentTime.getTime());
				hotelRoomNumEntity.setMoneyId(hotelRoomMoneyEntity.getId());
				hotelRoomNumEntity.setNums(hotelRoomMoneyEntity.getNum());
				hotelRoomNumEntity.setRid(hotelRoomMoneyEntity.getRoomId());
				hotelRoomNumService.save(hotelRoomNumEntity);
			}
			hotelOrderRecordEntity = new HotelOrderRecordEntity();
			hotelOrderRecordEntity.setRoomId(hotelRoomMoneyEntity.getRoomId());
			hotelOrderRecordEntity.setArrivalTime(DateUtil.date(curentTime));
			hotelOrderRecordEntity.setAmount(new BigDecimal(amount));
			hotelOrderRecordEntity.setCreateTime(DateUtil.date());
			hotelOrderRecordEntity.setMoneyId(createOrderForm.getMoneyId());
			hotelOrderRecordEntity.setOrderId(orderId);
			if (null != hotelRoomPriceEntity) {
				hotelOrderRecordEntity.setPriceId(hotelRoomPriceEntity.getId());
			}
			hotelOrderRecordEntity.setSellerId(createOrderForm.getSellerId());
			hotelOrderRecordEntity.setRoomType(hotelRoomEntity.getName() + "-" + hotelRoomMoneyEntity.getName());
			// 扣减房量
			hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, curentTime.getTime(), -createOrderForm.getRoomNum());
			orderRecordEntities.add(hotelOrderRecordEntity);
			// 时间后移
			curentTime = DateUtil.offsetDay(curentTime, 1);
		}
		hotelOrderRecordService.saveBatch(orderRecordEntities);
		log.debug("创建订单明细--end,result:{}", JSON.toJSONString(orderRecordEntities));
	}

	// 获取区间日期每一天
	public List<String> findDates(String dBegin, String dEnd) {
		List<String> lDate = new ArrayList<String>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(DateUtil.parse(dBegin));
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(DateUtil.parse(dEnd));
		// 测试此日期是否在指定日期之后
		while (DateUtil.parse(dEnd).after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(DateUtil.formatDate(calBegin.getTime()));
		}
		return lDate;
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
		hotelOrderVo.setRemark(hotelOrderEntity.getRemark());
		hotelOrderVo.setDdTime(hotelOrderEntity.getDdTime());
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
		hotelOrderVo.setSellerTel(hotelSellerEntity.getTel());
		hotelOrderVo.setCoordinates(hotelSellerEntity.getCoordinates());
		hotelOrderVo.setTotalCost(NumberUtil.decimalFormat("0.00", hotelOrderEntity.getTotalCost().doubleValue()));
		HotelInvoiceEntity hotelInvoiceEntity = hotelInvoiceDao.selectById(hotelOrderEntity.getInvoiceId());
		// 房间类型
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		hotelOrderVo.setClassify(hotelRoomEntity.getClassify());
		if (null != hotelInvoiceEntity) {
			hotelOrderVo.setInvoiceTitle(hotelInvoiceEntity.getCompany());
		}
		hotelOrderVo.setPrepay(hotelRoomMoneyEntity.getPrepay());
		// 用户积分 余额
		HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, hotelRoomEntity.getSellerId()).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
		if (null != hotelMemberLevelDetailEntity) {
			hotelOrderVo.setMemberBalance(hotelMemberLevelDetailEntity.getBalance());
			hotelOrderVo.setMemberIntegral(hotelMemberLevelDetailEntity.getScore());
		}
		hotelOrderVo.setPayIntegral(hotelRoomMoneyEntity.getIntegral());
		// 订单明细
		List<HotelOrderRecordEntity> hotelOrderRecordEntities = hotelOrderRecordService.list(Wrappers.<HotelOrderRecordEntity>lambdaQuery().eq(HotelOrderRecordEntity::getOrderId, orderId));
		OrderDetail orderDetail = null;
		for (HotelOrderRecordEntity hotelOrderRecordEntity : hotelOrderRecordEntities) {
			orderDetail = new OrderDetail();
			orderDetail.setAmount(NumberUtil.decimalFormat("0.00", hotelOrderRecordEntity.getAmount().doubleValue()));
			orderDetail.setDate(DateUtil.format(hotelOrderRecordEntity.getArrivalTime(), "yyyy-MM-dd"));
			orderDetail.setNum(hotelOrderEntity.getNum());
			hotelOrderVo.getRecord().add(orderDetail);
		}
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
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(hotelOrderEntity.getUserId());
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		// 恢复房量
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());

		if (hotelRoomMoneyEntity.getPrepay() == 1) {
			// 判断订单状态
			if (hotelOrderEntity.getPayFlag() == 1) {
				if (hotelOrderEntity.getPayMethod().equals(PayMethodConstants.WX)) {
					log.info("取消订单--订单已支付，执行退款");
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
					// 恢复余额
					hotelMemberLevelDetailService.addBalance(hotelRoomEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
					// 添加流水
					HotelMemberLevelDetailEntity memberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getMemberId, hotelOrderEntity.getUserId()).eq(HotelMemberLevelDetailEntity::getSellerId, hotelOrderEntity.getSellerId()));
					hotelRechargeService.addConsumptionRecord(hotelOrderEntity.getTotalCost().intValue(), memberLevelDetailEntity.getLevelId(), userId, "取消订房，" + hotelOrderEntity.getRoomType());
				}
				if (hotelOrderEntity.getPayMethod().equals(PayMethodConstants.INTEGRAL)) {
					// 恢复积分
					hotelMemberLevelDetailService.addIntegral(hotelOrderEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
					// 添加积分流水
					hotelScoreService.transactionScore(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), 2, hotelOrderEntity.getTotalCost().intValue(), "取消订房，" + hotelOrderEntity.getRoomType());
				}
			}
			if (hotelOrderEntity.getPayFlag() == 0) {
				log.info("取消订单--订单未支付，直接取消");
				hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
			}
		}
		List<HotelOrderRecordEntity> hotelOrderRecordEntities = hotelOrderRecordService.list(Wrappers.<HotelOrderRecordEntity>lambdaQuery().eq(HotelOrderRecordEntity::getOrderId, orderId));
		for (HotelOrderRecordEntity hotelOrderRecordEntity : hotelOrderRecordEntities) {
			// 恢复每天的房量
			hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, hotelOrderRecordEntity.getArrivalTime().getTime(), hotelOrderEntity.getNum());
		}
		hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
		// 更新订单
		this.updateById(hotelOrderEntity);

		// 恢复用户优惠券
		if (null != hotelOrderEntity.getCouponsId() && 0L != hotelOrderEntity.getCouponsId()) {
			HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, hotelOrderEntity.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 2).eq(HotelMemberCouponsEntity::getCouponsId, hotelOrderEntity.getCouponsId()));
			if (null == hotelMemberCouponsEntity) {
				throw new RRException("用户优惠券不存在");
			}
			hotelMemberCouponsEntity.setState(1);
			hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
		}
		// 用户早餐券
		if (null != hotelOrderEntity.getBreakCouponId() && 0L != hotelOrderEntity.getBreakCouponId()) {
			HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, hotelOrderEntity.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 3).eq(HotelMemberCouponsEntity::getCouponsId, hotelOrderEntity.getBreakCouponId()));
			if (null == hotelMemberCouponsEntity) {
				throw new RRException("用户早餐券不存在");
			}
			hotelMemberCouponsEntity.setState(1);
			hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
		}
		// 用户免房券
		if (null != hotelOrderEntity.getFreeRoomCouponId() && 0L != hotelOrderEntity.getFreeRoomCouponId()) {
			HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, hotelOrderEntity.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 1).eq(HotelMemberCouponsEntity::getCouponsId, hotelOrderEntity.getFreeRoomCouponId()));
			if (null == hotelMemberCouponsEntity) {
				throw new RRException("用户免房券不存在");
			}
			hotelMemberCouponsEntity.setState(1);
			hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
		}

		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		// 发送取消订房通知
		List<WxMaTemplateData> data = new ArrayList<WxMaTemplateData>();
		data.add(new WxMaTemplateData("first", "您好，您的订单已取消"));
		data.add(new WxMaTemplateData("keyword1", hotelOrderEntity.getOrderNo()));
		data.add(new WxMaTemplateData("keyword2", hotelOrderEntity.getSellerName()));
		data.add(new WxMaTemplateData("keyword3", hotelOrderEntity.getRoomType()));
		data.add(new WxMaTemplateData("keyword4", hotelOrderEntity.getNum().toString()));
		data.add(new WxMaTemplateData("keyword5", DateUtil.format(hotelOrderEntity.getArrivalTime(), "yyyy-MM-dd")));
		data.add(new WxMaTemplateData("keyword6", DateUtil.format(hotelOrderEntity.getDepartureTime(), "yyyy-MM-dd")));
		data.add(new WxMaTemplateData("keyword7", hotelOrderEntity.getTotalCost().toString()));
		WxMaTemplateMessage maTemplateMessage = new WxMaTemplateMessage(hotelMemberEntity.getOpenid(), "8Gs6-DAfpKktBMZmWJBPVnMzC3_MlP9Sz0ug4JrFqeg", null, formId, data, null);
		try {
			WxMaConfiguration.getMaService(hotelWxConfigEntity.getAppId()).getMsgService().sendTemplateMsg(maTemplateMessage);
		} catch (WxErrorException e) {
			log.error("发送取消订单微信模板消息失败：result：{}", e.getMessage());
			e.printStackTrace();
		}
		log.info("取消订单--end,result:success");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
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
			hotelOrderEntity.setPayFlag(1); // 支付状态

			this.updateById(hotelOrderEntity);
			this.updateOrderStatusWithOrderSetting(hotelOrderEntity);
			// 发送模板支付成功通知 TODO 目前采用异步线程，后期要改为消息队列

			hotelMemberLevelDetailService.addIntegral(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), hotelOrderEntity.getTotalCost());
			// 增加积分
			hotelScoreService.transactionScore(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), 2, hotelOrderEntity.getTotalCost().intValue(), "订单支付成功");

		} catch (WxPayException e) {
			log.error("search wx order error,outTradeNo:{}", outTradeNo);
			throw new RRException("查询微信支付订单异常");
		}
		log.info("更新订单为支付成功--end");
	}

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public WxPayMpOrderResult payOrder(Long userId, Long orderId, String ip, String payMethod, String formId) throws WxPayException {
		HotelOrderEntity hotelOrderEntity = this.getById(orderId);
		// 商户微信信息
		HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
		HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
		hotelOrderEntity.setOrderNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS" + userId));
		hotelOrderEntity.setPayMethod(payMethod);
		////
		if (PayMethodConstants.BALANCE.equals(payMethod)) {
			// 扣除余额
			hotelMemberLevelDetailService.balanceTransaction(hotelRoomEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
			// 更新订单
			this.updateOrderStatus2Payed(hotelOrderEntity.getId());
			// 发送预定通知
			sendReserveMessage(hotelOrderEntity, hotelSellerEntity, hotelMemberEntity, hotelWxConfigEntity, formId);
			// 添加流水
			HotelMemberLevelDetailEntity memberLevelDetailEntity = hotelMemberLevelDetailService.getOne(Wrappers.<HotelMemberLevelDetailEntity>lambdaQuery().eq(HotelMemberLevelDetailEntity::getSellerId, hotelOrderEntity.getSellerId()).eq(HotelMemberLevelDetailEntity::getMemberId, userId));
			hotelRechargeService.addConsumptionRecord(-hotelOrderEntity.getTotalCost().intValue(), memberLevelDetailEntity.getLevelId(), userId, "在线预定，" + hotelOrderEntity.getRoomType());
			this.updateOrderStatusWithOrderSetting(hotelOrderEntity);
			return null;
		} else if (PayMethodConstants.INTEGRAL.equals(payMethod)) {
			// 扣除积分
			hotelMemberLevelDetailService.integralTransaction(hotelRoomEntity.getSellerId(), userId, hotelOrderEntity.getTotalCost());
			// 更新订单
			this.updateOrderStatus2Payed(hotelOrderEntity.getId());
			// 发送预定通知
			sendReserveMessage(hotelOrderEntity, hotelSellerEntity, hotelMemberEntity, hotelWxConfigEntity, formId);
			// 添加积分流水
			hotelScoreService.transactionScore(hotelOrderEntity.getSellerId(), hotelOrderEntity.getUserId(), 2, hotelOrderEntity.getTotalCost().intValue(), "在线预定，" + hotelOrderEntity.getRoomType());
			this.updateOrderStatusWithOrderSetting(hotelOrderEntity);
			return null;
		} else if (PayMethodConstants.WX.equals(payMethod)) {
			log.info("调用微信统一下单--start,userId:{},sellerId:{},params:{}", userId, hotelRoomEntity.getSellerId(), JSON.toJSONString(hotelOrderEntity));
			WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
			wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
			wxPayUnifiedOrderRequest.setBody(hotelSellerEntity.getName() + "-" + hotelRoomEntity.getName() + "(" + hotelOrderEntity.getDays() + "晚)");
			wxPayUnifiedOrderRequest.setOutTradeNo(hotelOrderEntity.getOutTradeNo());
			wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
			wxPayUnifiedOrderRequest.setNotifyUrl("https://hotelapi.xqtinfo.cn/pay/" + hotelWxConfigEntity.getAppId() + "/notify/order");
			wxPayUnifiedOrderRequest.setTradeType("JSAPI");
			wxPayUnifiedOrderRequest.setTotalFee(1);
			wxPayUnifiedOrderRequest.setAttach(JSON.toJSONString(new OrderType(OrderTypeConstants.order_room, formId)));
			wxPayUnifiedOrderRequest.setSpbillCreateIp(ip);
			WxPayMpOrderResult mpOrderResult = WxPayConfiguration.getPayServices().get(hotelWxConfigEntity.getAppId()).createOrder(wxPayUnifiedOrderRequest);
			log.info("调用微信统一下单--start,result:{}", JSON.toJSONString(mpOrderResult));
			hotelOrderEntity.setFormId(mpOrderResult.getPackageValue().substring("prepay_id=".length(), mpOrderResult.getPackageValue().length()));
			baseMapper.updateById(hotelOrderEntity);
			return mpOrderResult;
		} else {
			throw new RRException("请选择支付方式");
		}
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
		IPage<HotelOrderEntity> page = this.page(new Query<HotelOrderEntity>().getPage(params), new QueryWrapper<HotelOrderEntity>()//
				.eq("status", HotelOrderStatus.UN_PAY)//
				.le("create_time", DateUtil.offset(DateUtil.date(), DateField.MINUTE, -30)));
		List<HotelOrderEntity> hotelOrderEntities = page.getRecords();
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
			// 恢复房量
			HotelRoomEntity hotelRoomEntity = hotelRoomService.getById(hotelOrderEntity.getRoomId());
			hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
			List<HotelOrderRecordEntity> hotelOrderRecordEntities = hotelOrderRecordService.list(Wrappers.<HotelOrderRecordEntity>lambdaQuery().eq(HotelOrderRecordEntity::getOrderId, hotelOrderEntity.getId()));
			for (HotelOrderRecordEntity hotelOrderRecordEntity : hotelOrderRecordEntities) {
				// 恢复每天的房量
				hotelRoomService.updateRoomNum(hotelRoomEntity, hotelRoomMoneyEntity, hotelOrderRecordEntity.getArrivalTime().getTime(), hotelOrderEntity.getNum());
			}
			// 恢复用户优惠券
			if (null != hotelOrderEntity.getCouponsId() && 0L != hotelOrderEntity.getCouponsId()) {
				HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, hotelOrderEntity.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 2).eq(HotelMemberCouponsEntity::getCouponsId, hotelOrderEntity.getCouponsId()));
				if (null == hotelMemberCouponsEntity) {
					throw new RRException("用户优惠券不存在");
				}
				hotelMemberCouponsEntity.setState(1);
				hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
			}
			// 用户早餐券
			if (null != hotelOrderEntity.getBreakCouponId() && 0L != hotelOrderEntity.getBreakCouponId()) {
				HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, hotelOrderEntity.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 3).eq(HotelMemberCouponsEntity::getCouponsId, hotelOrderEntity.getBreakCouponId()));
				if (null == hotelMemberCouponsEntity) {
					throw new RRException("用户早餐券不存在");
				}
				hotelMemberCouponsEntity.setState(1);
				hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
			}
			// 用户免房券
			if (null != hotelOrderEntity.getFreeRoomCouponId() && 0L != hotelOrderEntity.getFreeRoomCouponId()) {
				HotelMemberCouponsEntity hotelMemberCouponsEntity = hotelMemberCouponsService.getOne(Wrappers.<HotelMemberCouponsEntity>lambdaQuery().eq(HotelMemberCouponsEntity::getUserId, hotelOrderEntity.getUserId()).eq(HotelMemberCouponsEntity::getCouponsType, 1).eq(HotelMemberCouponsEntity::getCouponsId, hotelOrderEntity.getFreeRoomCouponId()));
				if (null == hotelMemberCouponsEntity) {
					throw new RRException("用户免房券不存在");
				}
				hotelMemberCouponsEntity.setState(1);
				hotelMemberCouponsService.updateById(hotelMemberCouponsEntity);
			}
		}
		if (CollectionUtil.isNotEmpty(hotelOrderEntities)) {
			this.updateBatchById(hotelOrderEntities);
		}
		// 发送取消订单通知
		ThreadUtil.execute(new Runnable() {
			@Override
			public void run() {
				// 获取酒店取消订单微信消息模板
				List<WxMaTemplateData> data = null;
				HotelMemberEntity hotelMemberEntity = null;
				WxMpTemplateMessage templateMessage = null;
				WxMpService mpService = null;
				HotelWxTemplateEntity hotelWxTemplateEntity = null;
				HotelWxConfigEntity hotelWxConfigEntity = hotelWxConfigService.getOne(new QueryWrapper<HotelWxConfigEntity>());
				for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
					hotelMemberEntity = hotelMemberService.getById(hotelOrderEntity.getUserId());
					if (null != hotelWxConfigEntity) {
						// 发送取消订房通知
						data = new ArrayList<WxMaTemplateData>();
						data.add(new WxMaTemplateData("first", "您好，您的订单已取消"));
						data.add(new WxMaTemplateData("keyword1", hotelOrderEntity.getOrderNo()));
						data.add(new WxMaTemplateData("keyword2", hotelOrderEntity.getSellerName()));
						data.add(new WxMaTemplateData("keyword3", hotelOrderEntity.getRoomType()));
						data.add(new WxMaTemplateData("keyword4", hotelOrderEntity.getNum().toString()));
						data.add(new WxMaTemplateData("keyword5", DateUtil.format(hotelOrderEntity.getArrivalTime(), "yyyy-MM-dd")));
						data.add(new WxMaTemplateData("keyword6", DateUtil.format(hotelOrderEntity.getDepartureTime(), "yyyy-MM-dd")));
						data.add(new WxMaTemplateData("keyword7", hotelOrderEntity.getTotalCost().toString()));
						WxMaTemplateMessage maTemplateMessage = new WxMaTemplateMessage(hotelMemberEntity.getOpenid(), "8Gs6-Fd9nEk2KlaHR80YSIYaVNL1fMg25DDSj4IXkPynorUA", null, hotelOrderEntity.getFormId(), data, null);
						try {
							WxMaConfiguration.getMaService(hotelWxConfigEntity.getAppId()).getMsgService().sendTemplateMsg(maTemplateMessage);
						} catch (WxErrorException e) {
							log.error("发送取消订单微信模板消息失败：result：{}", e.getMessage());
							e.printStackTrace();
						}
					}
				}

			}
		});
		log.info("自动取消订单--end");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void completeOrder() {
		DateTime date = DateUtil.offsetDay(DateUtil.date(), 15);
		List<HotelOrderEntity> hotelOrderEntities = baseMapper.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, HotelOrderStatus.WAIT_COMMENT).gt(HotelOrderEntity::getCreateTime, date));
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			hotelOrderEntity.setStatus(HotelOrderStatus.COMPLETE);
			baseMapper.updateById(hotelOrderEntity);
		}
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
		baseMapper.updateById(hotelOrderEntity);
	}

	@Override
	@Transactional
	public void checkInOrderTask() {
		int sys = 24; // 系统每天自动入住订单时间
		// 当日小时数
		int hour = DateUtil.hour(DateUtil.date(), true);
		List<HotelOrderEntity> hotelOrderEntities = baseMapper.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, HotelOrderStatus.WAIT_CHECK_IN).le(HotelOrderEntity::getArrivalTime, DateUtil.date()));
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			// 获取商家自动入住时间

			// 未设置就以系统时间为准
			if (hour == sys) {
				hotelOrderEntity.setStatus(HotelOrderStatus.CHECK_IN);
				baseMapper.updateById(hotelOrderEntity);
			}
		}
	}

	@Override
	@Transactional
	public void orderAutoCheckOut() {
		int sys = 12; // 系统离店时间
		int hour = DateUtil.hour(DateUtil.date(), true);
		List<HotelOrderEntity> hotelOrderEntities = baseMapper.selectList(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getStatus, HotelOrderStatus.CHECK_IN).le(HotelOrderEntity::getArrivalTime, DateUtil.date()));
		for (HotelOrderEntity hotelOrderEntity : hotelOrderEntities) {
			if (hour >= sys) {
				hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_COMMENT); // 更新订单为待评价
				baseMapper.updateById(hotelOrderEntity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void orderCheckIn(Long orderId) {
		HotelOrderEntity hotelOrderEntity = baseMapper.selectById(orderId);
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		if (hotelRoomMoneyEntity.getPrepay() == 1 && hotelOrderEntity.getStatus().intValue() != HotelOrderStatus.PAYED) {
			throw new RRException("非法操作，订单状态不正确");
		}
		hotelOrderEntity.setStatus(HotelOrderStatus.CHECK_IN);
		baseMapper.updateById(hotelOrderEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void orderCheckIn(Long orderId, Long userId) {
		HotelOrderEntity hotelOrderEntity = baseMapper.selectOne(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getUserId, userId).eq(HotelOrderEntity::getId, orderId));
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		if (hotelRoomMoneyEntity.getPrepay() == 1 && hotelOrderEntity.getStatus().intValue() != HotelOrderStatus.PAYED) {
			throw new RRException("非法操作，订单状态不正确");
		}
		hotelOrderEntity.setStatus(HotelOrderStatus.CHECK_IN);
		baseMapper.updateById(hotelOrderEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void orderAffirm(Long id, Long sellerId) {
		HotelOrderEntity hotelOrderEntity = baseMapper.selectOne(Wrappers.<HotelOrderEntity>lambdaQuery().eq(HotelOrderEntity::getSellerId, sellerId).eq(HotelOrderEntity::getId, id));
		if (null == hotelOrderEntity) {
			throw new RRException("非法操作");
		}
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		if (hotelRoomMoneyEntity.getPrepay() == 0) {
			hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_CHECK_IN);
		} else {
			if (hotelOrderEntity.getPayFlag() == 1) {
				hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_CHECK_IN);
			}
		}
		// 支付单逻辑处理
		baseMapper.updateById(hotelOrderEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateOrderStatusWithOrderSetting(HotelOrderEntity hotelOrderEntity) {
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(hotelOrderEntity.getMoneyId());
		// 查看订单是否需要确认
		HotelOrderSettingEntity hotelOrderSettingEntity = hotelOrderSettingService.getOne(Wrappers.<HotelOrderSettingEntity>lambdaQuery().eq(HotelOrderSettingEntity::getSellerId, hotelOrderEntity.getSellerId()));
		if (null != hotelOrderSettingEntity && hotelOrderSettingEntity.getAutoOrder().intValue() == 1 && (hotelRoomMoneyEntity.getPrepay() == 0 || hotelOrderEntity.getPayFlag() == 1)) {
			hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_AFFIRM);
			// 适用房型
			List<HotelOrderSettingRoomEntity> hotelOrderSettingRoomEntities = hotelOrderSettingRoomService.list(Wrappers.<HotelOrderSettingRoomEntity>lambdaQuery().eq(HotelOrderSettingRoomEntity::getSettingId, hotelOrderSettingEntity.getId()).eq(HotelOrderSettingRoomEntity::getRoomId, hotelOrderEntity.getRoomId()));
			if (CollectionUtil.isNotEmpty(hotelOrderSettingRoomEntities)) {
				hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_CHECK_IN);
			}
			// 适用时间
			DateTime now = DateUtil.parse(DateUtil.format(hotelOrderEntity.getArrivalTime(), "yyyy-MM-dd"));
			int day = now.dayOfWeek(); // 1-周日 以此类推
			List<HotelOrderSettingDateEntity> hotelOrderSettingDateEntities = hotelOrderSettingDateService.list(Wrappers.<HotelOrderSettingDateEntity>lambdaQuery().eq(HotelOrderSettingDateEntity::getSettingId, hotelOrderSettingEntity.getId()).eq(HotelOrderSettingDateEntity::getType, 2).eq(HotelOrderSettingDateEntity::getDate, day));
			if (CollectionUtil.isNotEmpty(hotelOrderSettingDateEntities)) {
				hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_CHECK_IN);
			}
			// 不适用时间
			hotelOrderSettingDateEntities = hotelOrderSettingDateService.list(Wrappers.<HotelOrderSettingDateEntity>lambdaQuery().eq(HotelOrderSettingDateEntity::getSettingId, hotelOrderSettingEntity.getId()).eq(HotelOrderSettingDateEntity::getType, 1).eq(HotelOrderSettingDateEntity::getDate, hotelOrderEntity.getArrivalTime()));
			if (CollectionUtil.isNotEmpty(hotelOrderSettingDateEntities)) {
				hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_AFFIRM);
			}
		} else {
			if (hotelRoomMoneyEntity.getPrepay() == 1 && hotelOrderEntity.getPayFlag() == 0) {
				hotelOrderEntity.setStatus(HotelOrderStatus.UN_PAY);
			} else {
				hotelOrderEntity.setStatus(HotelOrderStatus.WAIT_AFFIRM);
			}
		}
		baseMapper.updateById(hotelOrderEntity);

	}
}