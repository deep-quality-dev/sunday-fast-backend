package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelInvoiceDao;
import io.renren.modules.hotel.entity.HotelInvoiceEntity;
import io.renren.modules.hotel.service.HotelInvoiceService;


@Service("hotelInvoiceService")
public class HotelInvoiceServiceImpl extends ServiceImpl<HotelInvoiceDao, HotelInvoiceEntity> implements HotelInvoiceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotelInvoiceEntity> page = this.page(
                new Query<HotelInvoiceEntity>().getPage(params),
                new QueryWrapper<HotelInvoiceEntity>()
        );

        return new PageUtils(page);
    }

}