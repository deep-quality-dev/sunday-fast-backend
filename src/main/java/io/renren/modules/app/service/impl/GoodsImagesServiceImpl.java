package io.renren.modules.app.service.impl;

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

import cn.hutool.core.collection.CollectionUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.GoodsImagesDao;
import io.renren.modules.app.entity.GoodsImagesEntity;
import io.renren.modules.app.service.GoodsImagesService;

@Service("goodsImagesService")
public class GoodsImagesServiceImpl extends ServiceImpl<GoodsImagesDao, GoodsImagesEntity> implements GoodsImagesService {

	@Autowired
	private GoodsImagesDao goodsImagesDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<GoodsImagesEntity> page = this.page(new Query<GoodsImagesEntity>().getPage(params), new QueryWrapper<GoodsImagesEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveGoodsImgs(Integer goodsId, List<String> imgs) {
		List<GoodsImagesEntity> goodsImagesEntities = imgs.stream().map(img -> {
			GoodsImagesEntity goodsImagesEntity = new GoodsImagesEntity();
			goodsImagesEntity.setGid(goodsId);
			goodsImagesEntity.setImg(img);
			return goodsImagesEntity;
		}).collect(Collectors.toList());
		if (CollectionUtil.isNotEmpty(goodsImagesEntities)) {
			this.saveBatch(goodsImagesEntities);
		}
	}

	@Override
	public List<String> getGoodsImages(Integer goodsId) {
		List<GoodsImagesEntity> goodsImagesEntities = this.list(Wrappers.<GoodsImagesEntity>query().lambda().eq(GoodsImagesEntity::getGid, goodsId));
		return goodsImagesEntities.stream().map((GoodsImagesEntity goodsImagesEntity) -> {
			return goodsImagesEntity.getImg();
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delGoodsImgByGoodsId(Integer goodsId) {
		goodsImagesDao.delGoodsImgByGoodsId(goodsId);
	}

}