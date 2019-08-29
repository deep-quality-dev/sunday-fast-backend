package io.renren.modules.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.Query;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.dao.SpecItemDao;
import io.renren.modules.app.entity.SpecItemEntity;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.form.SpecItemForm;
import io.renren.modules.app.service.SpecItemService;

@Service("specItemService")
public class SpecItemServiceImpl extends ServiceImpl<SpecItemDao, SpecItemEntity> implements SpecItemService {

	@Autowired
	private SpecItemDao specItemDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SpecItemEntity> page = this.page(new Query<SpecItemEntity>().getPage(params), new QueryWrapper<SpecItemEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<SpecItemForm> saveSpecItem(SpecForm specForm) {
		List<SpecItemForm> specItemForms = specForm.getSpecItems();
		specItemForms.forEach((SpecItemForm item) -> {
			SpecItemEntity specItemEntity = new SpecItemEntity();
			specItemEntity.setItem(item.getItem());
			specItemEntity.setSpecId(item.getSpecId());
			this.save(specItemEntity);
			item.setId(specItemEntity.getId());
			item.setSpecId(specForm.getId());
		});
		return specItemForms;
	}

	@Override
	public List<SpecItemEntity> getSpecListByGoodsId(Integer goodsId, Integer specId) {

		List<SpecItemEntity> specItemEntities = specItemDao.getSpecListByGoodsId(goodsId,specId);

		return specItemEntities;
	}

}