package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelWxTemplateDao;
import io.renren.modules.hotel.entity.HotelWxTemplateEntity;
import io.renren.modules.hotel.service.HotelWxTemplateService;


@Service("hotelWxTemplateService")
public class HotelWxTemplateServiceImpl extends ServiceImpl<HotelWxTemplateDao, HotelWxTemplateEntity> implements HotelWxTemplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelWxTemplateEntity> page = this.page(
                new Query<HotelWxTemplateEntity>().getPage(params),
                new QueryWrapper<HotelWxTemplateEntity>()
        );

        return new PageUtils(page);
    }

}