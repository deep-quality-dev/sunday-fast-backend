package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.ConfigDao;
import io.renren.modules.app.entity.ConfigEntity;
import io.renren.modules.app.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigEntity> implements ConfigService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<ConfigEntity> page = this.page(new Query<ConfigEntity>().getPage(params), new QueryWrapper<ConfigEntity>());

		return new PageUtils(page);
	}

}