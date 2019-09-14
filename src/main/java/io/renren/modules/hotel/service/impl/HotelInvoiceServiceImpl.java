package io.renren.modules.hotel.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.hotel.dao.HotelInvoiceDao;
import io.renren.modules.hotel.entity.HotelInvoiceEntity;
import io.renren.modules.hotel.form.AddInvoiceForm;
import io.renren.modules.hotel.service.HotelInvoiceService;

@Service("hotelInvoiceService")
public class HotelInvoiceServiceImpl extends ServiceImpl<HotelInvoiceDao, HotelInvoiceEntity> implements HotelInvoiceService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelInvoiceEntity> page = this.page(new Query<HotelInvoiceEntity>().getPage(params), new QueryWrapper<HotelInvoiceEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addInvoice(Long userId, AddInvoiceForm addInvoiceForm) {
		HotelInvoiceEntity hotelInvoiceEntity = new HotelInvoiceEntity();
		BeanUtil.copyProperties(addInvoiceForm, hotelInvoiceEntity);
		hotelInvoiceEntity.setMemberId(userId);
		baseMapper.insert(hotelInvoiceEntity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delInvoice(Long userId, Long id) {
		baseMapper.delete(Wrappers.<HotelInvoiceEntity>lambdaQuery().eq(HotelInvoiceEntity::getMemberId, userId).eq(HotelInvoiceEntity::getId, id));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updInvoice(Long userId, AddInvoiceForm addInvoiceForm) {
		HotelInvoiceEntity hotelinvoiceentity = baseMapper.selectOne(Wrappers.<HotelInvoiceEntity>lambdaQuery().eq(HotelInvoiceEntity::getMemberId, userId).eq(HotelInvoiceEntity::getId, addInvoiceForm.getId()));
		if (null == hotelinvoiceentity) {
			throw new RRException("非法操作");
		}
		BeanUtil.copyProperties(addInvoiceForm, hotelinvoiceentity, "id");
		baseMapper.updateById(hotelinvoiceentity);
	}

	@Override
	public List<AddInvoiceForm> invoiceList(Long userId) {
		List<HotelInvoiceEntity> hotelInvoiceEntities = baseMapper.selectList(Wrappers.<HotelInvoiceEntity>lambdaQuery().eq(HotelInvoiceEntity::getMemberId, userId));
		List<AddInvoiceForm> addInvoiceForms = hotelInvoiceEntities.stream().map((HotelInvoiceEntity item) -> {
			AddInvoiceForm addInvoiceForm = new AddInvoiceForm();
			BeanUtil.copyProperties(item, addInvoiceForm);
			return addInvoiceForm;
		}).collect(Collectors.toList());
		return addInvoiceForms;
	}

}