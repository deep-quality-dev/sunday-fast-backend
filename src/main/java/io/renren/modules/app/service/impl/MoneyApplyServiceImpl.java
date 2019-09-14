package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.MoneyApplyDao;
import io.renren.modules.app.entity.MoneyApplyEntity;
import io.renren.modules.app.service.MoneyApplyService;

@Service("moneyApplyService")
public class MoneyApplyServiceImpl extends ServiceImpl<MoneyApplyDao, MoneyApplyEntity> implements MoneyApplyService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<MoneyApplyEntity> page = this.page(new Query<MoneyApplyEntity>().getPage(params), new QueryWrapper<MoneyApplyEntity>());

		return new PageUtils(page);
	}

}