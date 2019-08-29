package io.renren.modules.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.UploadGroupEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-06-05 15:33:58
 */
public interface UploadGroupService extends IService<UploadGroupEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
