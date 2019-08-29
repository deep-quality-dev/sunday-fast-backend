package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.DistributeDao;
import io.renren.modules.app.entity.DistributeEntity;
import io.renren.modules.app.service.DistributeService;

@Service("distributeService")
public class DistributeServiceImpl extends ServiceImpl<DistributeDao, DistributeEntity> implements DistributeService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<DistributeEntity> page = this.page(new Query<DistributeEntity>().getPage(params), new QueryWrapper<DistributeEntity>());

		return new PageUtils(page);
	}

}