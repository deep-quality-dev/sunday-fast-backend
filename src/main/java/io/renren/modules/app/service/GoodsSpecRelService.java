package io.renren.modules.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.GoodsSpecRelEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-23 15:54:23
 */
public interface GoodsSpecRelService extends IService<GoodsSpecRelEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
