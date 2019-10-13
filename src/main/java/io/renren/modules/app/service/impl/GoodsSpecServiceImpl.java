package io.renren.modules.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.GoodsSpecDao;
import io.renren.modules.app.entity.GoodsSpecEntity;
import io.renren.modules.app.entity.GoodsSpecRelEntity;
import io.renren.modules.app.entity.SpecEntity;
import io.renren.modules.app.entity.SpecItemEntity;
import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.form.SpecItemForm;
import io.renren.modules.app.service.GoodsSpecRelService;
import io.renren.modules.app.service.GoodsSpecService;
import io.renren.modules.app.service.SpecItemService;
import io.renren.modules.app.service.SpecService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("goodsSpecService")
public class GoodsSpecServiceImpl extends ServiceImpl<GoodsSpecDao, GoodsSpecEntity> implements GoodsSpecService {

	@Autowired
	private GoodsSpecRelService goodsSpecRelService;

	@Autowired
	private SpecItemService specItemService;

	@Autowired
	private SpecService specService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<GoodsSpecEntity> page = this.page(new Query<GoodsSpecEntity>().getPage(params), new QueryWrapper<GoodsSpecEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addGoodsSpec(Integer goodsId, List<GoodsSku> goodsSkus) {
		List<GoodsSpecEntity> goodsSpecEntities = new ArrayList<>();
		GoodsSpecEntity goodsSpecEntity = null;
		for (GoodsSku goodsSku : goodsSkus) {
			goodsSpecEntity = new GoodsSpecEntity();
			goodsSpecEntity.setGid(goodsId);
			goodsSpecEntity.setImg(goodsSku.getSkuImg());
			goodsSpecEntity.setSkuKey(goodsSku.getGoodsSkuKey());
			goodsSpecEntity.setSkuKeyValue(goodsSku.getGoodsSkuValue());
			goodsSpecEntity.setLinePrice(goodsSku.getLinePrice());
			goodsSpecEntity.setPrice(goodsSku.getSellPrice());
			goodsSpecEntity.setStore(goodsSku.getStock());
			goodsSpecEntity.setWeight(goodsSku.getWeight());
			goodsSpecEntities.add(goodsSpecEntity);
		}
		if (CollectionUtil.isNotEmpty(goodsSpecEntities)) {
			this.saveBatch(goodsSpecEntities);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateGoodsSpec(Integer goodsId, List<GoodsSku> goodsSkus) {
		goodsSkus.forEach((GoodsSku goodsSku) -> {
			GoodsSpecEntity goodsSpecEntity = this.getOne(Wrappers.<GoodsSpecEntity>lambdaQuery().eq(GoodsSpecEntity::getSkuKey, goodsSku.getGoodsSkuKey()));
			if (null == goodsSpecEntity) {
				goodsSpecEntity = new GoodsSpecEntity();
			}
			goodsSpecEntity.setGid(goodsId);
			goodsSpecEntity.setImg(goodsSku.getSkuImg());
			goodsSpecEntity.setSkuKey(goodsSku.getGoodsSkuKey());
			goodsSpecEntity.setSkuKeyValue(goodsSku.getGoodsSkuValue());
			goodsSpecEntity.setLinePrice(goodsSku.getLinePrice());
			goodsSpecEntity.setPrice(goodsSku.getSellPrice());
			goodsSpecEntity.setStore(goodsSku.getStock());
			goodsSpecEntity.setWeight(goodsSku.getWeight());
			this.saveOrUpdate(goodsSpecEntity);
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addGoodsSpecRel(Integer goodsId, List<SpecForm> specs) {
		List<GoodsSpecRelEntity> goodsSpecRelEntities = new ArrayList<GoodsSpecRelEntity>();
		GoodsSpecRelEntity goodsSpecRelEntity = null;
		for (SpecForm specForm : specs) {
			List<SpecItemForm> specItems = specForm.getSpecItems();
			for (SpecItemForm specItem : specItems) {
				goodsSpecRelEntity = new GoodsSpecRelEntity();
				goodsSpecRelEntity.setGoodsId(goodsId);
				goodsSpecRelEntity.setSpecId(specForm.getId());
				goodsSpecRelEntity.setSpecValueId(specItem.getId());
				goodsSpecRelEntities.add(goodsSpecRelEntity);
			}
		}
		goodsSpecRelService.saveBatch(goodsSpecRelEntities);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updaeGoodsSpecRel(Integer goodsId, List<SpecForm> specs) {
		// 先删除之前的关系数据
		goodsSpecRelService.remove(Wrappers.<GoodsSpecRelEntity>lambdaQuery().eq(GoodsSpecRelEntity::getGoodsId, goodsId));
		this.addGoodsSpecRel(goodsId, specs);
	}

	@Override
	public List<SpecForm> getSpecListByGoodsId(Integer goodsId) {
		List<SpecForm> specForms = new ArrayList<SpecForm>();
		SpecForm specForm = null;
		List<SpecItemForm> specItems = new ArrayList<SpecItemForm>();
		SpecItemForm specItemForm = null;
		List<GoodsSpecRelEntity> goodsSpecRelEntities = goodsSpecRelService.list(Wrappers.<GoodsSpecRelEntity>query().lambda().eq(GoodsSpecRelEntity::getGoodsId, goodsId));
		List<Integer> specList = goodsSpecRelEntities.stream().map(a -> a.getSpecId()).collect(Collectors.toList());
		specList = specList.stream().distinct().collect(Collectors.toList());
		List<SpecEntity> specEntities = (List<SpecEntity>) specService.listByIds(specList);
		for (SpecEntity specEntity : specEntities) {
			specForm = new SpecForm();
			BeanUtil.copyProperties(specEntity, specForm);
			List<SpecItemEntity> specItemEntities = specItemService.getSpecListByGoodsId(goodsId, specEntity.getId());
			specItems = new ArrayList<SpecItemForm>();
			for (SpecItemEntity specItemEntity : specItemEntities) {
				specItemForm = new SpecItemForm();
				BeanUtil.copyProperties(specItemEntity, specItemForm);
				specItems.add(specItemForm);
			}
			specForm.setSpecItems(specItems);
			specForms.add(specForm);
		}
		return specForms;
	}

	@Override
	public List<GoodsSku> getGoodsSkuList(Integer goodsId) {
		List<GoodsSku> goodsSkus = new ArrayList<GoodsSku>();
//		GoodsSku goodsSku = null;
		List<GoodsSpecEntity> goodsSpecEntities = this.list(Wrappers.<GoodsSpecEntity>query().lambda().eq(GoodsSpecEntity::getGid, goodsId));
		goodsSkus = goodsSpecEntities.stream().map((GoodsSpecEntity goodsSpecEntity) -> {
			GoodsSku goodsSku = new GoodsSku();
			goodsSku.setId(goodsSpecEntity.getId());
			goodsSku.setGoodsId(goodsSpecEntity.getGid());
			goodsSku.setGoodsNo(null);
			goodsSku.setSkuImg(goodsSpecEntity.getImg());
			goodsSku.setLinePrice(goodsSpecEntity.getLinePrice());
			goodsSku.setSellPrice(goodsSpecEntity.getPrice());
			goodsSku.setGoodsSkuKey(goodsSpecEntity.getSkuKey());
			goodsSku.setGoodsSkuValue(goodsSpecEntity.getSkuKeyValue());
			goodsSku.setStock(goodsSpecEntity.getStore());
			goodsSku.setWeight(goodsSpecEntity.getWeight());
			return goodsSku;
		}).collect(Collectors.toList());
//		for (GoodsSpecEntity goodsSpecEntity : goodsSpecEntities) {
//			goodsSku = new GoodsSku();
//			goodsSku.setGoodsId(goodsSpecEntity.getGid());
//			goodsSku.setGoodsNo(null);
//			goodsSku.setImg(goodsSpecEntity.getImg());
//			goodsSku.setLinePrice(goodsSpecEntity.getLinePrice());
//			goodsSku.setSellPrice(goodsSpecEntity.getPrice());
//			goodsSku.setSkuKey(goodsSpecEntity.getKey());
//			goodsSku.setSkuValue(goodsSpecEntity.getKeyValue());
//			goodsSku.setStock(goodsSpecEntity.getStore());
//			goodsSku.setWeight(goodsSpecEntity.getWeight());
//			goodsSkus.add(goodsSku);
//		}
		return goodsSkus;
	}

}