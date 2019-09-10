package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.AssessTagDao;
import io.renren.modules.hotel.entity.AssessTagEntity;
import io.renren.modules.hotel.service.AssessTagService;


@Service("assessTagService")
public class AssessTagServiceImpl extends ServiceImpl<AssessTagDao, AssessTagEntity> implements AssessTagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssessTagEntity> page = this.page(
                new Query<AssessTagEntity>().getPage(params),
                new QueryWrapper<AssessTagEntity>()
        );

        return new PageUtils(page);
    }

}