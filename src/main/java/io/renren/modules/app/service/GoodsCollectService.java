package io.renren.modules.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.GoodsCollectEntity;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface GoodsCollectService extends IService<GoodsCollectEntity> {

	PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加商品收藏
     * @param goodsId
     * @param userId
     */
    void addGoodsCollect(Integer goodsId, Integer userId);

    /**
     * 查询商品收藏
     * @param goodsId
     * @param userId
     * @return
     */
    boolean getGoodsCollect(Integer goodsId, Integer userId);

    /**
     * 删除商品收藏
     * @param goodsId
     * @param userId
     */
    void delGoodsCollect(Integer goodsId, Integer userId);
}
