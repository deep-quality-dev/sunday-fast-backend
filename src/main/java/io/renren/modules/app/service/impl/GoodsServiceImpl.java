package io.renren.modules.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import io.renren.modules.app.constans.CommonConstants;
import io.renren.modules.app.dao.GoodsDao;
import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.form.EditGoodsForm;
import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.service.GoodsImagesService;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.service.GoodsSpecService;
import io.renren.modules.app.service.SpecService;
import io.renren.modules.app.vo.GoodsDetailVo;
import io.renren.modules.app.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Autowired
    private SpecService specService;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Autowired
    private GoodsImagesService goodsImageService;

    @Override
    public IPage getGoodsPage(Page page, GoodsDto goodsDto) {
        return baseMapper.getGoodsPage(page, goodsDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addGoods(EditGoodsForm goods) {
        log.info(JSON.toJSONString(goods));
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtil.copyProperties(goods, goodsEntity);
        if (CollectionUtil.isNotEmpty(goods.getImgs())) {
            goodsEntity.setImg(goods.getImgs().get(0));
        }
        baseMapper.insert(goodsEntity);
        goodsImageService.saveGoodsImgs(goodsEntity.getId(), goods.getImgs());
        if (CommonConstants.SPEC_TYPE_MULTITON.equals(goods.getSpecType())) {
            Assert.isTrue(CollectionUtil.isNotEmpty(goods.getGoodsSkus()), "商品SKU不能为空");
            goodsSpecService.addGoodsSpec(goodsEntity.getId(), goods.getGoodsSkus());
            goodsSpecService.addGoodsSpecRel(goodsEntity.getId(), goods.getSpecs());
        }
    }

    @Override
    public EditGoodsForm goodsInfo(Integer goodsId) {
        EditGoodsForm editGoodsForm = new EditGoodsForm();
        GoodsEntity goodsEntity = this.getById(goodsId);
        BeanUtil.copyProperties(goodsEntity, editGoodsForm);
        if (CommonConstants.SPEC_TYPE_MULTITON.equals(goodsEntity.getSpecType())) {
            List<SpecForm> specs = goodsSpecService.getSpecListByGoodsId(goodsId);
            editGoodsForm.setSpecs(specs);
            List<GoodsSku> goodsSkus = goodsSpecService.getGoodsSkuList(goodsId);
            editGoodsForm.setGoodsSkus(goodsSkus);
            List<String> imgs = goodsImageService.getGoodsImages(goodsId);
            editGoodsForm.setImgs(imgs);
        }
        return editGoodsForm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGoods(EditGoodsForm editGoodsForm) {
        GoodsEntity goodsEntity = this.getById(editGoodsForm.getId());
        BeanUtil.copyProperties(editGoodsForm, goodsEntity, "id");
        if (CollectionUtil.isNotEmpty(editGoodsForm.getImgs())) {
            goodsEntity.setImg(editGoodsForm.getImgs().get(0));
        }
        baseMapper.updateById(goodsEntity);
        //先删除图片在保存
        goodsImageService.delGoodsImgByGoodsId(editGoodsForm.getId());
        log.debug("del goods imges success ");
        goodsImageService.saveGoodsImgs(goodsEntity.getId(), editGoodsForm.getImgs());
        log.debug("save goods imges success ");
        if (CommonConstants.SPEC_TYPE_MULTITON.equals(editGoodsForm.getSpecType())) {
            Assert.isTrue(CollectionUtil.isNotEmpty(editGoodsForm.getGoodsSkus()), "商品SKU不能为空");
            goodsSpecService.updateGoodsSpec(goodsEntity.getId(), editGoodsForm.getGoodsSkus());
            log.debug("update goods spec success ");
            goodsSpecService.updaeGoodsSpecRel(goodsEntity.getId(), editGoodsForm.getSpecs());
            log.debug("update goods spec rel success ");
        }
    }

    @Override
    public Page<GoodsVo> goodsList(Page<GoodsVo> page, GoodsDto params) {
        return baseMapper.goodsList(page, params);
    }

    @Override
    public GoodsDetailVo goodsDetail(Integer goodsId, Integer userId) {
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        GoodsEntity goodsEntity = this.getById(goodsId);
        if (null == goodsEntity || goodsEntity.getEnabled() != CommonConstants.ENABLED) {
            throw new RuntimeException("商品信息未找到");
        }
        BeanUtil.copyProperties(goodsEntity, goodsDetailVo);
        List<String> imgs = goodsImageService.getGoodsImages(goodsId);
        goodsDetailVo.setGoodsImages(imgs);

        List<GoodsSku> goodsSkus = new ArrayList<>();
        //查询商品sku列表
        if (CommonConstants.SPEC_TYPE_MULTITON == goodsEntity.getSpecType()) {
            goodsSkus = goodsSpecService.getGoodsSkuList(goodsId);
            List<SpecForm> specs = goodsSpecService.getSpecListByGoodsId(goodsId);
            if (CollectionUtil.isEmpty(goodsSkus)) {
                throw new RuntimeException("sku 为空");
            }
            goodsDetailVo.setSpecs(specs);
            goodsDetailVo.setGoodsSkus(goodsSkus);
        }
        return goodsDetailVo;
    }
}