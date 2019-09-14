package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.Query;
import io.renren.modules.app.dao.UploadFileDao;
import io.renren.modules.app.entity.UploadFileEntity;
import io.renren.modules.app.service.UploadFileService;

@Service("uploadFileService")
public class UploadFileServiceImpl extends ServiceImpl<UploadFileDao, UploadFileEntity> implements UploadFileService {

	@Override
	public IPage<UploadFileEntity> queryPage(Page<UploadFileEntity> page, Map<String, Object> params) {
		IPage<UploadFileEntity> ipage = this.page(new Query<UploadFileEntity>().getPage(params), new QueryWrapper<UploadFileEntity>());
		return ipage;
	}

}