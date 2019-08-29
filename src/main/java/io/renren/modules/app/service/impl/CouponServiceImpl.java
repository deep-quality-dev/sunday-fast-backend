package io.renren.modules.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.CouponDao;
import io.renren.modules.app.entity.CouponEntity;
import io.renren.modules.app.service.CouponService;

@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, CouponEntity> implements CouponService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<CouponEntity> page = this.page(new Query<CouponEntity>().getPage(params), new QueryWrapper<CouponEntity>());

		return new PageUtils(page);
	}

}