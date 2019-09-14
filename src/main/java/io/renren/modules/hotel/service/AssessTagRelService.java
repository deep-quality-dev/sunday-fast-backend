package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.AssessTagRelEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-10 16:26:07
 */
public interface AssessTagRelService extends IService<AssessTagRelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

