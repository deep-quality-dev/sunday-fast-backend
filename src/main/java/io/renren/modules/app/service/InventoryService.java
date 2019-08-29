package io.renren.modules.app.service;

/**
 * 库存服务
 */
public interface InventoryService {

    /**
     * 校验商品库存
     * @param goodsId
     * @param skuKey
     * @param num
     * @return
     */
    public int checkInventory(Integer goodsId,String skuKey,int num);
}
