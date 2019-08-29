package io.renren.modules.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.SpecEntity;
import io.renren.modules.app.form.SpecForm;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface SpecService extends IService<SpecEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 
	 * @param specs
	 */
	public void saveOrModifySpecWithItem(List<SpecForm> specs);

	/**
	 * 保存规格
	 * 
	 * @param spec
	 * @return
	 */
	SpecForm saveSpec(SpecForm spec);
}
