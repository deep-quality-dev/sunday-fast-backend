package io.renren.modules.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.exception.RRException;
import io.renren.modules.app.constans.CommonConstants;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.entity.GoodsSpecEntity;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.service.GoodsSpecService;
import io.renren.modules.app.service.InventoryService;

/**
 * 商品库存服务
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Override
    public int checkInventory(Integer goodsId, String skuKey, int num) {
        GoodsEntity goodsEntity = goodsService.getById(goodsId);
        if (null == goodsEntity) {
            throw new RRException("商品不存在");
        }
        if (CommonConstants.SPEC_TYPE_MULTITON == goodsEntity.getSpecType()) {
            GoodsSpecEntity goodsSpecEntity = goodsSpecService.getOne(Wrappers.<GoodsSpecEntity>lambdaQuery().eq(GoodsSpecEntity::getGid, goodsId).eq(GoodsSpecEntity::getSkuKey, skuKey));
            if (null == goodsSpecEntity) {
                throw new RRException("商品Sku不存在");
            }
            if (goodsSpecEntity.getStore() - num < 0) {
                return -1;
            }
        } else {
            if (goodsEntity.getSort() - num < 0) {
                return -1;
            }
        }
        return 1;
    }
}
