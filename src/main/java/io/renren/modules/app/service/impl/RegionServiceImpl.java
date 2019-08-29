package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.Query;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.dao.RegionDao;
import io.renren.modules.app.entity.RegionEntity;
import io.renren.modules.app.service.RegionService;

@Service("regionService")
public class RegionServiceImpl extends ServiceImpl<RegionDao, RegionEntity> implements RegionService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<RegionEntity> page = this.page(new Query<RegionEntity>().getPage(params), new QueryWrapper<RegionEntity>());

		return new PageUtils(page);
	}

}