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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.constants.HotelOrderStatus;
import io.renren.modules.hotel.dao.HotelOrderDao;
import io.renren.modules.hotel.entity.HotelMemberEntity;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.entity.HotelOrderRecordEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.entity.HotelRoomPriceEntity;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.form.BuildOrderForm;
import io.renren.modules.hotel.form.CreateOrderForm;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.service.HotelOrderRecordService;
import io.renren.modules.hotel.service.HotelOrderService;
import io.renren.modules.hotel.service.HotelRoomMoneyService;
import io.renren.modules.hotel.service.HotelRoomPriceService;
import io.renren.modules.hotel.service.HotelRoomService;
import io.renren.modules.hotel.service.HotelScoreService;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.service.TransactionService;
import io.renren.modules.hotel.vo.HotelOrderVo;
import io.renren.modules.hotel.vo.OrderDetail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hotelOrderService")
public class HotelOrderServiceImpl extends ServiceImpl<HotelOrderDao, HotelOrderEntity> implements HotelOrderService {
	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
	@Autowired
	private WxPayService wxService;

	@Autowired
	private HotelRoomService hotelRoomService;

	@Autowired
	private HotelRoomMoneyService hotelRoomMoneyService;

	@Autowired
	private HotelRoomPriceService hotelRoomPriceService;

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

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelOrderEntity> page = this.page(new Query<HotelOrderEntity>().getPage(params), new QueryWrapper<HotelOrderEntity>());

		return new PageUtils(page);
	}

	@Override
	public BuildOrderForm buildOrder(Long sellerId, Long userId, Long roomId, Long moneyId, int roomNum, String checkInDate, String checkOutDate) {
		log.info("构建订单信息--start,sellerId:{},userId,{},roomId:{},moneyId:{},checkInDate:{},checkOutDate:{}", sellerId, userId, roomId, moneyId, checkInDate, checkOutDate);
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
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		if (null != hotelMemberEntity) {
			buildOrderForm.setCheckInPerson(hotelMemberEntity.getZsName());
			buildOrderForm.setMobile(hotelMemberEntity.getTel());
		}
		// 查询房价
		HotelRoomMoneyEntity hotelRoomMoneyEntity = hotelRoomMoneyService.getById(moneyId);
		HotelRoomPriceEntity hotelRoomPriceEntity = null;
		DateTime curentTime = new DateTime(DateUtil.parse(checkInDate));
		for (int i = 0; i < checkInDay; i++) {
			orderDetail = new OrderDetail();
			// 是否有特殊价格
			long thisdateTimes = curentTime.millsecond();// 当天时间戳
			log.info("查询每日房价--start，time:{},roomId:{},moneyId:{},sellerId:{}", thisdateTimes, roomId, moneyId, sellerId);
			hotelRoomPriceEntity = hotelRoomPriceService.getOne(new QueryWrapper<HotelRoomPriceEntity>().eq("seller_id", sellerId).eq("money_id", moneyId).eq("room_id", roomId).eq("thisdate", thisdateTimes));
			// 时间后移
			curentTime = DateUtil.offsetDay(curentTime, 1);
			Double amount = 0.0;
			if (null != hotelRoomPriceEntity) {
				log.info("日期:{},存在特殊价格:{}", curentTime, hotelRoomPriceEntity.getMprice());
				// 使用特殊价格--暂时取会员价
				amount = NumberUtil.mul(roomNum, hotelRoomPriceEntity.getMprice().doubleValue());
			} else {
				// 使用原价--暂时取会员价
				amount = NumberUtil.mul(roomNum, hotelRoomMoneyEntity.getMprice().doubleValue());
			}
			totalAmount = NumberUtil.add(totalAmount, amount);
			orderDetail.setAmount(NumberUtil.decimalFormat("0.00", amount));
			orderDetail.setNum(roomNum);
			log.info("查询每日房价--end，result:{}", JSON.toJSONString(orderDetail));
			orderDetails.add(orderDetail);
		}
		buildOrderForm.setTotalAmount(NumberUtil.decimalFormat("0.00", totalAmount));
		BigDecimal totalAmountFen = NumberUtil.mul(totalAmount, new BigDecimal(100));
		buildOrderForm.setTotalAmountFen(totalAmountFen.intValue());
		buildOrderForm.getRecord().addAll(orderDetails);
		log.info("构建订单信息--end,result:{}", JSON.toJSONString(buildOrderForm));
		return buildOrderForm;
	}

	@Override
	@Transactional
	public WxPayMpOrderResult createOrder(BuildOrderForm buildOrderForm, Long userId, Long sellerId) throws WxPayException {
		CreateOrderForm createOrderForm = new CreateOrderForm();
		BuildOrderForm newBuildOrderForm = this.buildOrder(sellerId, userId, buildOrderForm.getRoomId(), buildOrderForm.getMoneyId(), buildOrderForm.getRoomNum(), buildOrderForm.getCheckInDate(), buildOrderForm.getCheckOutDate());
		BeanUtil.copyProperties(newBuildOrderForm, createOrderForm);
		createOrderForm.setRoomId(buildOrderForm.getRoomId());
		createOrderForm.setMoneyId(buildOrderForm.getMoneyId());
		createOrderForm.setSellerId(sellerId);
		createOrderForm.setUserId(userId);
		HotelOrderEntity hotelOrderEntity = this.createHotelOrder(createOrderForm);
		log.info("调用微信统一下单--start,userId:{},sellerId:{},params:{}", userId, sellerId, JSON.toJSONString(buildOrderForm));
		// 酒店信息
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(sellerId);
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setBody(hotelSellerEntity.getName() + "-" + createOrderForm.getRoomName() + "(" + createOrderForm.getCheckInDay() + "晚)");
		wxPayUnifiedOrderRequest.setOutTradeNo(hotelOrderEntity.getOutTradeNo());
		wxPayUnifiedOrderRequest.setSceneInfo(hotelSellerEntity.getAddress());
		wxPayUnifiedOrderRequest.setNotifyUrl("http://hotelapi.xqtinfo.cn/pay/notify/order");
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setTotalFee(1);
		wxPayUnifiedOrderRequest.setSpbillCreateIp(buildOrderForm.getIp());
		WxPayMpOrderResult mpOrderResult = wxService.createOrder(wxPayUnifiedOrderRequest);
		log.info("调用微信统一下单--start,result:{}", JSON.toJSONString(mpOrderResult));
		return mpOrderResult;
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
		hotelOrderEntity.setOrderNo(IdUtil.simpleUUID());
		hotelOrderEntity.setSellerName(hotelSellerEntity.getName());
		hotelOrderEntity.setSellerAddress(hotelSellerEntity.getAddress());
		hotelOrderEntity.setCoordinates(createOrderForm.getCoordinates());
		hotelOrderEntity.setArrivalTime(DateUtil.parse(createOrderForm.getCheckInDate()));
		hotelOrderEntity.setDepartureTime(DateUtil.parse(createOrderForm.getCheckOutDate()));
		hotelOrderEntity.setNum(createOrderForm.getRoomNum());
		hotelOrderEntity.setDays(Integer.valueOf(String.valueOf(createOrderForm.getCheckInDay())));
		hotelOrderEntity.setRoomType(hotelRoomEntity.getName());
		hotelOrderEntity.setName(createOrderForm.getCheckInPerson());
		hotelOrderEntity.setOutTradeNo(IdUtil.simpleUUID());
		hotelOrderEntity.setStatus(HotelOrderStatus.UN_PAY);
		hotelOrderEntity.setTel(createOrderForm.getMobile());
		hotelOrderEntity.setCreateTime(DateUtil.date());
		hotelOrderEntity.setEnabled(1);
		hotelOrderEntity.setTotalCost(BigDecimal.valueOf(Long.valueOf(createOrderForm.getTotalAmountFen())).divide(new BigDecimal(100)));
		this.save(hotelOrderEntity);
		// 保存订单明细
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
				amount = NumberUtil.mul(createOrderForm.getRoomNum(), hotelRoomMoneyEntity.getMprice().doubleValue());
			}
			hotelOrderRecordEntity = new HotelOrderRecordEntity();
			hotelOrderRecordEntity.setAmount(new BigDecimal(amount));
			hotelOrderRecordEntity.setCreateTime(DateUtil.date());
			hotelOrderRecordEntity.setMoneyId(createOrderForm.getMoneyId());
			hotelOrderRecordEntity.setOrderId(orderId);
			hotelOrderRecordEntity.setPriceId(hotelRoomPriceEntity.getId());
			hotelOrderRecordEntity.setSellerId(createOrderForm.getSellerId());
			hotelOrderRecordEntity.setRoomType(hotelRoomEntity.getName() + "-" + hotelRoomMoneyEntity.getMotitle());
			orderRecordEntities.add(hotelOrderRecordEntity);
		}
		hotelOrderRecordService.saveBatch(orderRecordEntities);
		log.info("创建订单明细--end,result:{}", JSON.toJSONString(orderRecordEntities));
	}

	@Override
	public PageUtils userOrderList(Long userId, Long sellerId, Integer orderStatus, int page, int limie) {
		log.info("获取用户订单--start,userId:{},sellerId:{},orderStatus:{}", userId, sellerId, orderStatus);
		QueryWrapper<HotelOrderEntity> queryWrapper = new QueryWrapper<HotelOrderEntity>();
		if (null != orderStatus) {
			queryWrapper.eq("status", orderStatus);
		}
		queryWrapper.eq("seller_id", sellerId);
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
		return new PageUtils(hotelOrderVos, pageResult.getTotal(), pageResult.getSize(), pageResult.getCurrent());
	}

	@Override
	public HotelOrderVo orderDetail(Long sellerId, Long userId, Long orderId) {
		log.info("查询订单详情--start,sellerId:{},userId:{},orderId:{}", sellerId, userId, orderId);
		HotelOrderEntity hotelOrderEntity = this.getOne(new QueryWrapper<HotelOrderEntity>().eq("id", orderId).eq("seller_id", sellerId).eq("user_id", userId));
		if (null == hotelOrderEntity) {
			log.error("查询订单详情--参数错误，未找到订单信息,sellerId:{},userId:{},orderId:{}", sellerId, userId, orderId);
			throw new RRException("参数错误，未找到订单信息");
		}
		HotelOrderVo hotelOrderVo = new HotelOrderVo();
		BeanUtil.copyProperties(hotelOrderEntity, hotelOrderVo);
		HotelSellerEntity hotelSellerEntity = hotelSellerService.getById(hotelOrderEntity.getSellerId());
		hotelOrderVo.setSellerTel(hotelSellerEntity.getTel());
		hotelOrderVo.setTotalCost(NumberUtil.decimalFormat("0.00", hotelOrderEntity.getTotalCost().doubleValue()));
		log.info("查询订单详情--end,result:{}", JSON.toJSONString(hotelOrderVo));
		return hotelOrderVo;
	}

	@Override
	public void cancelOrder(Long sellerId, Long userId, Long orderId) {
		log.info("取消订单--start,sellerId:{},userId:{},orderId:{}", sellerId, userId, orderId);
		HotelOrderEntity hotelOrderEntity = this.getOne(new QueryWrapper<HotelOrderEntity>().eq("id", orderId).eq("seller_id", sellerId).eq("user_id", userId));
		if (null == hotelOrderEntity) {
			log.error("取消订单--参数错误，未找到订单信息,sellerId:{},userId:{},orderId:{}", sellerId, userId, orderId);
			throw new RRException("参数错误，未找到订单信息");
		}
		// 判断订单状态
		if (hotelOrderEntity.getStatus().intValue() == HotelOrderStatus.PAYED) {
			log.info("取消订单--订单已支付，执行退款");
			// 已经在线付款，需要判断是否满足取消规则 TODO
			Map<String, String> refundParams = new HashMap<String, String>();
			transactionService.refund(refundParams);
		}
		if (hotelOrderEntity.getStatus().intValue() == HotelOrderStatus.UN_PAY) {
			log.info("取消订单--订单未支付，直接取消");
			hotelOrderEntity.setStatus(HotelOrderStatus.CANCEL);
		}
		this.updateById(hotelOrderEntity);
		log.info("取消订单--end,result:success");
	}

	@Override
	public void updateOrderStatus2Payed(String outTradeNo) {
		log.info("更新订单为支付成功--start,outTradeNo:{}", outTradeNo);
		// 这里是否需要增加乐观锁，防止重复更新 TODO
		try {
			// SUCCESS—支付成功,REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败)
			WxPayOrderQueryResult payOrderQueryResult = wxService.queryOrder(null, outTradeNo);
			String wxOrderStatus = payOrderQueryResult.getTradeState();
			if (!"SUCCESS".equalsIgnoreCase(wxOrderStatus)) {
				log.warn("注意，支付回调，微信订单状态异常，wxOrderStatus:{}", wxOrderStatus);
				return;
			}
			HotelOrderEntity hotelOrderEntity = this.getOne(new QueryWrapper<HotelOrderEntity>().eq("out_trade_no", outTradeNo));
			if (null == hotelOrderEntity) {
				log.error("未找到订单信息,outTradeNo:{}", outTradeNo);
				return;
			}
			hotelOrderEntity.setStatus(HotelOrderStatus.PAYED);
			this.updateById(hotelOrderEntity);
			// 发送模板支付成功通知 TODO 目前采用异步线程，后期要改为消息队列
			// 增加积分
			hotelScoreService.transactionScore(hotelOrderEntity.getUserId(), hotelOrderEntity.getTotalCost().intValue(), 10, "订单支付成功");
		} catch (WxPayException e) {
			log.error("search wx order error,outTradeNo:{}", outTradeNo);
			throw new RRException("查询微信支付订单异常");
		}
		log.info("更新订单为支付成功--end");
	}

	@Override
	public WxPayUnifiedOrderResult payOrder(Long sellerId, Long userId, Long orderId, String ip) throws WxPayException {
		// 用户信息
		HotelMemberEntity hotelMemberEntity = hotelMemberService.getById(userId);
		HotelOrderEntity hotelOrderEntity = this.getById(orderId);
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setAppid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setBody(hotelOrderEntity.getSellerName() + "-" + hotelOrderEntity.getRoomType());
		wxPayUnifiedOrderRequest.setDetail(JSON.toJSONString(hotelOrderEntity));
		// wxPayUnifiedOrderRequest.setMchId("");TODO 暂时没做支持多公众号支付，默认读取配置文件商户信息
		wxPayUnifiedOrderRequest.setOpenid(hotelMemberEntity.getOpenid());
		wxPayUnifiedOrderRequest.setOutTradeNo(hotelOrderEntity.getOutTradeNo());
		wxPayUnifiedOrderRequest.setProductId(hotelOrderEntity.getRoomId().toString());
		wxPayUnifiedOrderRequest.setSceneInfo(hotelOrderEntity.getSellerAddress());
		// wxPayUnifiedOrderRequest.setNotifyUrl(notifyUrl);
		BigDecimal totalAmountFen = NumberUtil.mul(hotelOrderEntity.getTotalCost(), new BigDecimal(100));
		wxPayUnifiedOrderRequest.setTotalFee(totalAmountFen.intValue());
		wxPayUnifiedOrderRequest.setSpbillCreateIp(ip);
		WxPayUnifiedOrderResult payUnifiedOrderResult = wxService.unifiedOrder(wxPayUnifiedOrderRequest);
		return payUnifiedOrderResult;
	}
}