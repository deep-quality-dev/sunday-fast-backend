package io.renren.modules.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.modules.app.entity.UploadFileEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-06-05 15:33:58
 */
public interface UploadFileService extends IService<UploadFileEntity> {

	IPage<UploadFileEntity> queryPage(Page<UploadFileEntity> page, Map<String, Object> params);
}
