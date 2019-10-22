package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.MessageBoardEntity;

import java.util.Map;

/**
 * 留言板
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-22 21:41:35
 */
public interface MessageBoardService extends IService<MessageBoardEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

