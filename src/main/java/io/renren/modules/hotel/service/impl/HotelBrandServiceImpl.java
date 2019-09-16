package io.renren.modules.hotel.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelBrandDao;
import io.renren.modules.hotel.dao.HotelBrandTypeDao;
import io.renren.modules.hotel.entity.HotelBrandEntity;
import io.renren.modules.hotel.entity.HotelBrandTypeEntity;
import io.renren.modules.hotel.service.HotelBrandService;
import io.renren.modules.hotel.vo.HotelBrandTypeVo;
import io.renren.modules.hotel.vo.HotelBrandVo;

@Service("hotelBrandService")
public class HotelBrandServiceImpl extends ServiceImpl<HotelBrandDao, HotelBrandEntity> implements HotelBrandService {

	@Autowired
	private HotelBrandTypeDao hotelBrandTypeDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelBrandEntity> page = this.page(new Query<HotelBrandEntity>().getPage(params), new QueryWrapper<HotelBrandEntity>());

		return new PageUtils(page);
	}

	@Override
	public List<HotelBrandTypeVo> hotelBrandWithType() {
		List<HotelBrandTypeEntity> brandTypeEntities = hotelBrandTypeDao.selectList(Wrappers.lambdaQuery());
		List<HotelBrandTypeVo> brandTypeVos = brandTypeEntities.stream().map((HotelBrandTypeEntity item) -> {
			HotelBrandTypeVo brandTypeVo = new HotelBrandTypeVo();
			BeanUtil.copyProperties(item, brandTypeVo);
			return brandTypeVo;
		}).collect(Collectors.toList());
		for (HotelBrandTypeVo hotelBrandTypeVo : brandTypeVos) {
			List<HotelBrandVo> brandVos = this.loadBrandByType(hotelBrandTypeVo.getId());
			hotelBrandTypeVo.setBrands(brandVos);
		}
		return brandTypeVos;
	}

	@Override
	public List<HotelBrandVo> loadBrandByType(Long typeId) {
		List<HotelBrandEntity> brandEntities = baseMapper.selectList(Wrappers.<HotelBrandEntity>lambdaQuery().eq(HotelBrandEntity::getTypeId, typeId));
		List<HotelBrandVo> brandVos = brandEntities.stream().map((HotelBrandEntity item) -> {
			HotelBrandVo brandVo = new HotelBrandVo();
			BeanUtil.copyProperties(item, brandVo);
			return brandVo;
		}).collect(Collectors.toList());
		return brandVos;
	}

	@Override
	public List<HotelBrandVo> loadAllBrand() {
		List<HotelBrandEntity> brandEntities = baseMapper.selectList(Wrappers.lambdaQuery());
		List<HotelBrandVo> brandVos = brandEntities.stream().map((HotelBrandEntity item) -> {
			HotelBrandVo brandVo = new HotelBrandVo();
			BeanUtil.copyProperties(item, brandVo);
			return brandVo;
		}).collect(Collectors.toList());
		return brandVos;
	}

}