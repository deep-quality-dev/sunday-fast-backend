package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelOrderEntity;
import io.renren.modules.hotel.form.BuildOrderForm;
import io.renren.modules.hotel.vo.HotelOrderVo;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:34
 */
public interface HotelOrderService extends IService<HotelOrderEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 构建订信息
	 * 
	 * @param sellerId     商家ID
	 * @param userId       用户ID
	 * @param roomId       房型ID
	 * @param moneyId      房价ID
	 * @param contactsId
	 * @param roomNum      房间数量
	 * @param checkOutDate 离店时间
	 * @param checkInDate  入住时间
	 * @return
	 */
	BuildOrderForm buildOrder(Long userId, Long roomId, Long moneyId, Long contactsId, Long couponId, int roomNum, String checkInDate, String checkOutDate);

	/**
	 * 创建订单
	 * 
	 * @param buildOrderForm 房间订单信息
	 * @param userId         用户ID
	 * @return
	 */
	Long createOrder(BuildOrderForm buildOrderForm, Long userId) throws WxPayException;

	/**
	 * 用户订单列表
	 * 
	 * @param userId      用户ID
	 * @param orderStatus 订单状态
	 * @param limie       分页大小
	 * @param page        当前页
	 */
	Page<HotelOrderVo> userOrderList(Long sellerId, Integer orderStatus, int page, int limie);

	/**
	 * 订单详情
	 * 
	 * @param userId  用户ID
	 * @param orderId 订单ID
	 * @return
	 */
	HotelOrderVo orderDetail(Long userId, Long orderId);

	/**
	 * 取消订单
	 * 
	 * @param userId  用户ID
	 * @param orderId 订单ID
	 * @param formId  小程序模板消息
	 */
	void cancelOrder(Long userId, Long orderId, String formId);

	/**
	 * 更新订单状态为支付成功
	 * 
	 * @param outTradeNo 支付单号
	 */
	void updateOrderStatus2Payed(String outTradeNo);

	/**
	 * 更新订单为支付 用于积分支付，余额支付
	 * 
	 * @param orderId
	 */
	void updateOrderStatus2Payed(Long orderId);

	/**
	 * 根据订单号支付
	 * 
	 * @param userId    用户ID
	 * @param orderId   订单ID
	 * @param ip        下单IP
	 * @param formId
	 * @param payMethod
	 * @return
	 * @throws WxPayException
	 */
	WxPayMpOrderResult payOrder(Long userId, Long orderId, String ip, String payMethod, String formId) throws WxPayException;

	/**
	 * 删除订单
	 * 
	 * @param userId  用户ID
	 * @param orderId 订单ID
	 */
	void deleteOrder(Long userId, Long orderId);

	/**
	 * 自动取消订单
	 */
	public void updateOrder2Cancel();

	/**
	 * 自动完成订单
	 */
	void completeOrder();

	/**
	 * 更新订单为已退款
	 * 
	 * @param outTradeNo
	 */
	void updateOrderStatus2Refunded(String outTradeNo);

	/**
	 * 订单自动入住
	 */
	void checkInOrderTask();

	/**
	 * 订单自动离店
	 */
	void orderAutoCheckOut();

	/**
	 * 订单入住
	 * 
	 * @param orderId
	 */
	void orderCheckIn(Long orderId);

	void orderCheckIn(Long orderId, Long userId);

	/**
	 * 确认订单
	 * 
	 * @param id
	 * @param sellerId
	 */
	void orderAffirm(Long id, Long sellerId);

	/**
	 * 根据商家订单设置更改订单状态
	 * 
	 * @param hotelOrderEntity
	 */
	void updateOrderStatusWithOrderSetting(HotelOrderEntity hotelOrderEntity);
}
