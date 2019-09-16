package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelFacilityDao;
import io.renren.modules.hotel.entity.HotelFacilityEntity;
import io.renren.modules.hotel.service.HotelFacilityService;
import io.renren.modules.hotel.vo.FacilityVo;

@Service("hotelFacilityService")
public class HotelFacilityServiceImpl extends ServiceImpl<HotelFacilityDao, HotelFacilityEntity> implements HotelFacilityService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelFacilityEntity> page = this.page(new Query<HotelFacilityEntity>().getPage(params), new QueryWrapper<HotelFacilityEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<FacilityVo> hotelFacility(int type) {
		List<FacilityVo> facilityVos = new ArrayList<FacilityVo>();
		List<HotelFacilityEntity> hotelFacilityEntities = baseMapper.selectList(Wrappers.<HotelFacilityEntity>lambdaQuery().eq(HotelFacilityEntity::getType, type));
		facilityVos = hotelFacilityEntities.stream().map((HotelFacilityEntity item) -> {
			FacilityVo facilityVo = new FacilityVo();
			BeanUtil.copyProperties(item, facilityVo);
			return facilityVo;
		}).collect(Collectors.toList());
		return facilityVos;
	}

}