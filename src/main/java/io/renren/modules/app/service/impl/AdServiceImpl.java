package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.AdDao;
import io.renren.modules.app.entity.AdEntity;
import io.renren.modules.app.service.AdService;

@Service("adService")
public class AdServiceImpl extends ServiceImpl<AdDao, AdEntity> implements AdService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<AdEntity> page = this.page(new Query<AdEntity>().getPage(params), new QueryWrapper<AdEntity>());

		return new PageUtils(page);
	}

}