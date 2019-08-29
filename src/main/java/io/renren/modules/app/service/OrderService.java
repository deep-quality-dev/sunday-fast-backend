package io.renren.modules.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.vo.GoodsVo;
import io.renren.modules.app.vo.OrderVo;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface OrderService extends IService<OrderEntity> {

	/**
	 * 订单列表
	 * 
	 * @param page
	 * @param order
	 * @return
	 */
	IPage orderPage(Page page, OrderEntity order);

	/**
	 * 订单列表
	 * 
	 * @param page
	 * @param status
	 * @param userId
	 * @return
	 */
	Page<OrderVo> orderList(Page<GoodsVo> page, Integer status, Integer userId);

	/**
	 * 订单详情
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	OrderVo orderDetail(Integer id, Integer userId);

	/**
	 * 删除订单
	 * 
	 * @param orderId
	 * @param userId
	 */
	void delOrder(Integer orderId, Integer userId);
}
