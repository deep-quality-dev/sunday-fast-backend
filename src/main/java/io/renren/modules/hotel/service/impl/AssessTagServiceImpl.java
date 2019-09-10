package io.renren.modules.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.AssessTagDao;
import io.renren.modules.hotel.entity.AssessTagEntity;
import io.renren.modules.hotel.service.AssessTagService;

@Service("assessTagService")
public class AssessTagServiceImpl extends ServiceImpl<AssessTagDao, AssessTagEntity> implements AssessTagService {

	@Autowired
	private AssessTagDao assessTagDao;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<AssessTagEntity> page = this.page(new Query<AssessTagEntity>().getPage(params), new QueryWrapper<AssessTagEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<AssessTagEntity> hotelTags(Long sellerId) {
		return assessTagDao.hotelTags(sellerId);
	}

}