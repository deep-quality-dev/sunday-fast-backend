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
import io.renren.modules.hotel.dao.HotelContactsDao;
import io.renren.modules.hotel.entity.HotelContactsEntity;
import io.renren.modules.hotel.form.AddContactsForm;
import io.renren.modules.hotel.service.HotelContactsService;

@Service("hotelContactsService")
public class HotelContactsServiceImpl extends ServiceImpl<HotelContactsDao, HotelContactsEntity> implements HotelContactsService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelContactsEntity> page = this.page(new Query<HotelContactsEntity>().getPage(params), new QueryWrapper<HotelContactsEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addContacts(Long userId, AddContactsForm contactsForm) {
		HotelContactsEntity hotelContactsEntity = new HotelContactsEntity();
		BeanUtil.copyProperties(contactsForm, hotelContactsEntity);
		hotelContactsEntity.setMemberId(userId);
		baseMapper.insert(hotelContactsEntity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delContacts(Long userId, Long id) {
		baseMapper.delete(Wrappers.<HotelContactsEntity>lambdaQuery().eq(HotelContactsEntity::getMemberId, userId).eq(HotelContactsEntity::getId, id));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updContacts(Long userId, AddContactsForm contactsForm) {
		HotelContactsEntity hotelContactsEntity = baseMapper.selectOne(Wrappers.<HotelContactsEntity>lambdaQuery().eq(HotelContactsEntity::getMemberId, userId).eq(HotelContactsEntity::getId, contactsForm.getId()));
		if (null == hotelContactsEntity) {
			throw new RRException("非法操作");
		}
		hotelContactsEntity.setMobile(contactsForm.getMobile());
		hotelContactsEntity.setName(contactsForm.getName());
		baseMapper.updateById(hotelContactsEntity);
	}

	@Override
	public List<AddContactsForm> contactsList(Long userId) {
		List<HotelContactsEntity> contactsEntities = baseMapper.selectList(Wrappers.<HotelContactsEntity>lambdaQuery().eq(HotelContactsEntity::getMemberId, userId));
		List<AddContactsForm> addContactsForms = contactsEntities.stream().map((HotelContactsEntity item) -> {
			AddContactsForm addContactsForm = new AddContactsForm();
			BeanUtil.copyProperties(item, addContactsForm);
			return addContactsForm;
		}).collect(Collectors.toList());
		return addContactsForms;
	}

}