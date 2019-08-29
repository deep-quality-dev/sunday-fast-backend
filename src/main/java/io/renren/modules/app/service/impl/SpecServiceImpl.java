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

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.dao.SpecDao;
import io.renren.modules.app.entity.SpecEntity;
import io.renren.modules.app.entity.SpecItemEntity;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.form.SpecItemForm;
import io.renren.modules.app.service.SpecItemService;
import io.renren.modules.app.service.SpecService;

@Service("specService")
public class SpecServiceImpl extends ServiceImpl<SpecDao, SpecEntity> implements SpecService {

	@Autowired
	private SpecItemService specItemService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SpecEntity> page = this.page(new Query<SpecEntity>().getPage(params), new QueryWrapper<SpecEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrModifySpecWithItem(List<SpecForm> specs) {
		for (SpecForm spec : specs) {
			SpecEntity specEntity = null;
			if (null == spec.getId() || spec.getId() == 0) {
				specEntity = new SpecEntity();
				BeanUtil.copyProperties(spec, specEntity);
			} else {
				specEntity = baseMapper.selectById(spec.getId());
				BeanUtil.copyProperties(spec, specEntity);
			}
			this.saveOrUpdate(specEntity);
			for (SpecItemForm specItem : spec.getSpecItems()) {
				SpecItemEntity itemEntity = null;
				if (null == specItem.getId() || specItem.getId() == 0) {
					itemEntity = new SpecItemEntity();
					BeanUtil.copyProperties(specItem, itemEntity);
					itemEntity.setSpecId(specEntity.getId());
				} else {
					itemEntity = specItemService.getById(specItem.getId());
					BeanUtil.copyProperties(specItem, itemEntity);
				}
				specItemService.saveOrUpdate(itemEntity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SpecForm saveSpec(SpecForm specForm) {
		SpecForm returnSpec = new SpecForm();
		SpecEntity specEntity = new SpecEntity();
		specEntity.setName(specForm.getName());
		this.save(specEntity);
		specForm.setId(specEntity.getId());
		List<SpecItemForm> specItems = specItemService.saveSpecItem(specForm);
		BeanUtil.copyProperties(specForm, returnSpec);
		returnSpec.setSpecItems(specItems);
		return returnSpec;
	}
}