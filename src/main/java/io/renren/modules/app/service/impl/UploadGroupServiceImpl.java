package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.UploadGroupDao;
import io.renren.modules.app.entity.UploadGroupEntity;
import io.renren.modules.app.service.UploadGroupService;

@Service("uploadGroupService")
public class UploadGroupServiceImpl extends ServiceImpl<UploadGroupDao, UploadGroupEntity> implements UploadGroupService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<UploadGroupEntity> page = this.page(new Query<UploadGroupEntity>().getPage(params), new QueryWrapper<UploadGroupEntity>());

		return new PageUtils(page);
	}

}