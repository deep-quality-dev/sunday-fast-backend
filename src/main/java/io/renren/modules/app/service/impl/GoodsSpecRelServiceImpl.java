package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.GoodsSpecRelDao;
import io.renren.modules.app.entity.GoodsSpecRelEntity;
import io.renren.modules.app.service.GoodsSpecRelService;

@Service("goodsSpecRelService")
public class GoodsSpecRelServiceImpl extends ServiceImpl<GoodsSpecRelDao, GoodsSpecRelEntity> implements GoodsSpecRelService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<GoodsSpecRelEntity> page = this.page(new Query<GoodsSpecRelEntity>().getPage(params), new QueryWrapper<GoodsSpecRelEntity>());

		return new PageUtils(page);
	}

}