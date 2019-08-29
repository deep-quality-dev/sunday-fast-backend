package io.renren.modules.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.RegionEntity;

/**
 * 
 *
 * @author taoz
 * @email taozui1995@gmail.com
 * @date 2019-06-25 12:48:47
 */
public interface RegionService extends IService<RegionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

