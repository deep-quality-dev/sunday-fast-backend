package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.OrderGoodsDao;
import io.renren.modules.app.entity.OrderGoodsEntity;
import io.renren.modules.app.service.OrderGoodsService;

@Service("orderGoodsService")
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsDao, OrderGoodsEntity> implements OrderGoodsService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<OrderGoodsEntity> page = this.page(new Query<OrderGoodsEntity>().getPage(params), new QueryWrapper<OrderGoodsEntity>());

		return new PageUtils(page);
	}

}