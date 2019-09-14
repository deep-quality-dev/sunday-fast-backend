package io.renren.modules.hotel.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.AssessTagRelDao;
import io.renren.modules.hotel.entity.AssessTagRelEntity;
import io.renren.modules.hotel.service.AssessTagRelService;


@Service("assessTagRelService")
public class AssessTagRelServiceImpl extends ServiceImpl<AssessTagRelDao, AssessTagRelEntity> implements AssessTagRelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssessTagRelEntity> page = this.page(
                new Query<AssessTagRelEntity>().getPage(params),
                new QueryWrapper<AssessTagRelEntity>()
        );

        return new PageUtils(page);
    }

}