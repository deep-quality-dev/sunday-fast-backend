package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.CommentDao;
import io.renren.modules.app.entity.CommentEntity;
import io.renren.modules.app.service.CommentService;

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<CommentEntity> page = this.page(new Query<CommentEntity>().getPage(params), new QueryWrapper<CommentEntity>());

		return new PageUtils(page);
	}

}