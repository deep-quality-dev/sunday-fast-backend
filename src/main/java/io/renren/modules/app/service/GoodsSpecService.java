package io.renren.modules.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.GoodsSpecEntity;
import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.form.SpecForm;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface GoodsSpecService extends IService<GoodsSpecEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 保存goods sku
	 * 
	 * @param goodsId
	 * @param goodsSkus
	 */
	public void addGoodsSpec(Integer goodsId, List<GoodsSku> goodsSkus);


	/**
	 * 更新商品规格
	 * @param goodsId
	 * @param goodsSkus
	 */
	public void updateGoodsSpec(Integer goodsId, List<GoodsSku> goodsSkus);

	/**
	 * 商品规格关系
	 * 
	 * @param goodsId
	 * @param specs
	 */
	public void addGoodsSpecRel(Integer goodsId, List<SpecForm> specs);

	/**
	 * 更新商品规格关系
	 * @param goodsId
	 * @param specs
	 */
	public void updaeGoodsSpecRel(Integer goodsId, List<SpecForm> specs);

	/**
	 * 加载商品规格
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<SpecForm> getSpecListByGoodsId(Integer goodsId);

	/**
	 * 获取商品sku List
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<GoodsSku> getGoodsSkuList(Integer goodsId);

}
