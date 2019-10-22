package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.MessageBoardDao;
import io.renren.modules.hotel.entity.MessageBoardEntity;
import io.renren.modules.hotel.service.MessageBoardService;


@Service("messageBoardService")
public class MessageBoardServiceImpl extends ServiceImpl<MessageBoardDao, MessageBoardEntity> implements MessageBoardService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MessageBoardEntity> page = this.page(
                new Query<MessageBoardEntity>().getPage(params),
                new QueryWrapper<MessageBoardEntity>()
        );

        return new PageUtils(page);
    }

}