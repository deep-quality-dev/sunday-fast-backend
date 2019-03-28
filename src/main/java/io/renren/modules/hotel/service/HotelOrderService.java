package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
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
	 * @param roomNum      房间数量
	 * @param checkOutDate 离店时间
	 * @param checkInDate  入住时间
	 * @return
	 */
	BuildOrderForm buildOrder(Long sellerId, Long userId, Long roomId, Long moneyId, int roomNum, String checkInDate, String checkOutDate);

	/**
	 * 创建订单
	 * 
	 * @param buildOrderForm 房间订单信息
	 * @param userId         用户ID
	 * @param sellerId       商家ID
	 * @return
	 * @throws WxPayException
	 */
	WxPayMpOrderResult createOrder(BuildOrderForm buildOrderForm, Long userId, Long sellerId) throws WxPayException;

	/**
	 * 用户订单列表
	 * 
	 * @param userId      用户ID
	 * @param sellerId    商家ID
	 * @param orderStatus 订单状态
	 * @param limie       分页大小
	 * @param page        当前页
	 */
	PageUtils userOrderList(Long userId, Long sellerId, Integer orderStatus, int page, int limie);

	/**
	 * 订单详情
	 * 
	 * @param sellerId 商家ID
	 * @param userId   用户ID
	 * @param orderId  订单ID
	 * @return
	 */
	HotelOrderVo orderDetail(Long sellerId, Long userId, Long orderId);

	/**
	 * 取消订单
	 * 
	 * @param sellerId 商家ID
	 * @param userId   用户ID
	 * @param orderId  订单ID
	 */
	void cancelOrder(Long sellerId, Long userId, Long orderId);

	/**
	 * 更新订单状态为支付成功
	 * 
	 * @param outTradeNo 支付单号
	 */
	void updateOrderStatus2Payed(String outTradeNo);

	/**
	 * 根据订单号支付
	 * 
	 * @param sellerId 商家ID
	 * @param userId   用户ID
	 * @param orderId  订单ID
	 * @param ip       下单IP
	 * @return
	 * @throws WxPayException 
	 */
	WxPayUnifiedOrderResult payOrder(Long sellerId, Long userId, Long orderId, String ip) throws WxPayException;
}
