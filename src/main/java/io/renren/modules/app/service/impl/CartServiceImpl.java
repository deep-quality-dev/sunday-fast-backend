package io.renren.modules.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.constans.CommonConstants;
import io.renren.modules.app.dao.CartDao;
import io.renren.modules.app.entity.CartEntity;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.entity.GoodsSpecEntity;
import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.service.CartService;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.service.GoodsSpecService;
import io.renren.modules.app.service.InventoryService;
import io.renren.modules.app.vo.CartVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartDao, CartEntity> implements CartService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CartEntity> page = this.page(new Query<CartEntity>().getPage(params), new QueryWrapper<CartEntity>());

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void intoCart(GoodsSku goodsSku, int num, int memberId) {
        log.info("添加购物车，params：goods:{},num,userid:{}", JSON.toJSONString(goodsSku), num, memberId);
        Integer goodsId = goodsSku.getGoodsId();
        //检查库存
        int result = inventoryService.checkInventory(goodsId, goodsSku.getGoodsSkuKey(), num);
        if (result < 0) {
            throw new RRException("库存不足");
        }
        GoodsEntity goodsEntity = goodsService.getById(goodsId);
        if (null == goodsEntity) {
            throw new RRException("商品信息不存在");
        }
        BigDecimal goodsPrice = goodsEntity.getPrice();
        if (CommonConstants.SPEC_TYPE_MULTITON == goodsEntity.getSpecType()) {
            if (StrUtil.isEmpty(goodsSku.getGoodsSkuKey())) {
                throw new RRException("请选择商品规格");
            }
            GoodsSpecEntity goodsSpecEntity = goodsSpecService.getOne(Wrappers.<GoodsSpecEntity>lambdaQuery().eq(GoodsSpecEntity::getGid, goodsId).eq(GoodsSpecEntity::getSkuKey, goodsSku.getGoodsSkuKey()));
            goodsPrice = goodsSpecEntity.getPrice();
        }
        //TODO 加入购物车需要扣减库存吗？
        CartEntity cartEntity = new CartEntity();
        cartEntity.setGid(goodsId);
        cartEntity.setName(goodsEntity.getName());
        cartEntity.setNum(num);
        cartEntity.setPrice(goodsPrice);
        cartEntity.setSpecKey(goodsSku.getGoodsSkuKey());
        cartEntity.setSpecKeyValue(goodsSku.getGoodsSkuValue());
        cartEntity.setUid(memberId);
        baseMapper.insert(cartEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCart(Integer cartId, int memberId) {
        baseMapper.delete(Wrappers.<CartEntity>lambdaQuery().eq(CartEntity::getId, cartId).eq(CartEntity::getUid, memberId));
    }

    @Override
    public List<CartVo> cartList(Integer memberId) {
        List<CartVo> cartVoList = new ArrayList<>();
        List<CartEntity> cartEntityList = baseMapper.selectList(Wrappers.<CartEntity>lambdaQuery().eq(CartEntity::getUid, memberId));
        cartVoList = cartEntityList.stream().map((CartEntity item) -> {
            CartVo cartVo = new CartVo();
            BeanUtil.copyProperties(item, cartVo);
            return cartVo;
        }).collect(Collectors.toList());
        return cartVoList;
    }

}