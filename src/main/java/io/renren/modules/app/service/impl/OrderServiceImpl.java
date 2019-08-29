package io.renren.modules.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.exception.RRException;
import io.renren.modules.app.dao.OrderDao;
import io.renren.modules.app.dao.OrderGoodsDao;
import io.renren.modules.app.dto.OrderDto;
import io.renren.modules.app.dto.OrderGoodsDto;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.entity.OrderGoodsEntity;
import io.renren.modules.app.service.OrderGoodsService;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.vo.GoodsVo;
import io.renren.modules.app.vo.OrderItemVo;
import io.renren.modules.app.vo.OrderVo;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderGoodsDao orderGoodsDao;

	@Autowired
	private OrderGoodsService orderGoodsService;

	@Override
	public IPage orderPage(Page page, OrderEntity order) {
		IPage orderPage = this.page(page, Wrappers.query(order));
		List<OrderEntity> orderEntities = orderPage.getRecords();
		List<OrderDto> orderDtos = new ArrayList<OrderDto>();
		orderDtos = orderEntities.stream().map((OrderEntity orderEntity) -> {
			OrderDto orderDto = new OrderDto();
			BeanUtil.copyProperties(orderEntity, orderDto);
			List<OrderGoodsEntity> orderGoodsEntities = orderGoodsService.list(Wrappers.<OrderGoodsEntity>query().lambda().eq(OrderGoodsEntity::getOid, orderEntity.getId()));
			List<OrderGoodsDto> orderGoodsDtos = orderGoodsEntities.stream().map((OrderGoodsEntity orderGoods) -> {
				OrderGoodsDto orderGoodsDto = new OrderGoodsDto();
				BeanUtil.copyProperties(orderGoods, orderGoodsDto);
				return orderGoodsDto;
			}).collect(Collectors.toList());
			orderDto.setOrderGoods(orderGoodsDtos);
			return orderDto;
		}).collect(Collectors.toList());
		orderPage.setRecords(orderDtos);
		return orderPage;
	}

	@Override
	public Page<OrderVo> orderList(Page<GoodsVo> page, Integer status, Integer userId) {
		Page<OrderVo> orderPage = orderDao.orderList(page, status, userId);
		List<OrderVo> orderVos = orderPage.getRecords();
		for (OrderVo orderVo : orderVos) {
			List<OrderItemVo> orderItemVos = orderGoodsDao.getOrderGoods(orderVo.getId());
			orderVo.setOrderItem(orderItemVos);
		}
		orderPage.setRecords(orderVos);
		return orderPage;
	}

	@Override
	public OrderVo orderDetail(Integer id, Integer userId) {
		OrderVo orderVo = new OrderVo();
		OrderEntity orderEntity = baseMapper.selectById(id);
		if (null == orderEntity || orderEntity.getUid().intValue() != userId.intValue()) {
			throw new RRException("参数错误");
		}
		BeanUtil.copyProperties(orderEntity, orderVo);
		List<OrderItemVo> orderItemVos = orderGoodsDao.getOrderGoods(orderVo.getId());
		orderVo.setOrderItem(orderItemVos);
		return orderVo;
	}

	@Override
	public void delOrder(Integer orderId, Integer userId) {
		OrderEntity orderEntity = baseMapper.selectById(orderId);
		if (null == orderEntity || orderEntity.getUid().intValue() != userId.intValue()) {
			throw new RRException("参数错误");
		}
		// if(orderEntity.getOrderStatus().intValue() != CommonConstants.) 订单状态判断
		baseMapper.deleteById(orderId);
	}

}