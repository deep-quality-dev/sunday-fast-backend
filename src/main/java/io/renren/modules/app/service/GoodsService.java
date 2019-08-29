package io.renren.modules.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.form.EditGoodsForm;
import io.renren.modules.app.vo.GoodsDetailVo;
import io.renren.modules.app.vo.GoodsVo;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface GoodsService extends IService<GoodsEntity> {

	IPage getGoodsPage(Page page, GoodsDto goodsDto);

	/**
	 * 添加商品
	 * 
	 * @param goods
	 */
	public void addGoods(EditGoodsForm goods);

	/**
	 * 商品信息
	 * 
	 * @param id
	 * @return
	 */
	public EditGoodsForm goodsInfo(Integer id);

	/**
	 * 修改商品
	 * 
	 * @param goods
	 */
	public void updateGoods(EditGoodsForm goods);

	/**
	 * 商品列表
	 * @param page
	 * @param params
	 * @return
	 */
    Page<GoodsVo> goodsList(Page<GoodsVo> page, GoodsDto params);

	/**
	 * 商品详情
	 * @param goodsId
	 * @param userId
	 * @return
	 */
	GoodsDetailVo goodsDetail(Integer goodsId, Integer userId);
}
