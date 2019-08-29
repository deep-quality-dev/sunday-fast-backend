package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.GoodsCollectDao;
import io.renren.modules.app.entity.GoodsCollectEntity;
import io.renren.modules.app.service.GoodsCollectService;

@Service("goodsCollectService")
public class GoodsCollectServiceImpl extends ServiceImpl<GoodsCollectDao, GoodsCollectEntity> implements GoodsCollectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsCollectEntity> page = this.page(new Query<GoodsCollectEntity>().getPage(params), new QueryWrapper<GoodsCollectEntity>());

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addGoodsCollect(Integer goodsId, Integer userId) {
        GoodsCollectEntity goodsCollectEntity = baseMapper.selectOne(Wrappers.<GoodsCollectEntity>lambdaQuery().eq(GoodsCollectEntity::getGid, goodsId).eq(GoodsCollectEntity::getUid, userId));
        if (null == goodsCollectEntity) {
            goodsCollectEntity = new GoodsCollectEntity();
            goodsCollectEntity.setGid(goodsId);
            goodsCollectEntity.setUid(userId);
            baseMapper.insert(goodsCollectEntity);
        }else{
            baseMapper.delete(Wrappers.<GoodsCollectEntity>lambdaQuery().eq(GoodsCollectEntity::getGid, goodsId).eq(GoodsCollectEntity::getUid, userId));
        }
    }

    @Override
    public boolean getGoodsCollect(Integer goodsId, Integer userId) {
        GoodsCollectEntity goodsCollectEntity = baseMapper.selectOne(Wrappers.<GoodsCollectEntity>lambdaQuery().eq(GoodsCollectEntity::getGid, goodsId).eq(GoodsCollectEntity::getUid, userId));
        return goodsCollectEntity != null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delGoodsCollect(Integer goodsId, Integer userId) {
        baseMapper.delete(Wrappers.<GoodsCollectEntity>lambdaQuery().eq(GoodsCollectEntity::getGid, goodsId).eq(GoodsCollectEntity::getUid, userId));
    }

}