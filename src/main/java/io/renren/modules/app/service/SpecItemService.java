package io.renren.modules.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.SpecItemEntity;
import io.renren.modules.app.form.SpecForm;
import io.renren.modules.app.form.SpecItemForm;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-23 13:59:07
 */
public interface SpecItemService extends IService<SpecItemEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 保存规格值
	 * 
	 * @param specForm
	 * @return
	 */
	List<SpecItemForm> saveSpecItem(SpecForm specForm);

	/**
	 * 获取商品规格值
	 * @param goodsId
	 * @param id
	 * @return
	 */
    List<SpecItemEntity> getSpecListByGoodsId(Integer goodsId, Integer id);
}
