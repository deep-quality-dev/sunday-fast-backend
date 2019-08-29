package io.renren.modules.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.GoodsImagesEntity;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface GoodsImagesService extends IService<GoodsImagesEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 保存商品图片
	 * 
	 * @param imgs
	 */
	public void saveGoodsImgs(Integer goodsId, List<String> imgs);

	/**
	 * 获取商品图片
	 * 
	 * @param goodsId
	 * @return
	 */
	public List<String> getGoodsImages(Integer goodsId);

	/**
	 * 删除商品图片
	 * @param goodsId
	 */
    void delGoodsImgByGoodsId(Integer goodsId);
}
