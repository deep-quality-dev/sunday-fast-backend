package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.AssessTagEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-10 16:26:08
 */
public interface AssessTagService extends IService<AssessTagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

