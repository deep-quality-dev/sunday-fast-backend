package io.renren.modules.app.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.CommentEntity;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface CommentService extends IService<CommentEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
