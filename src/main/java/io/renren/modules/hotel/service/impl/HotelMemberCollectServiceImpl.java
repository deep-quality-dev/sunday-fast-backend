package io.renren.modules.hotel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.hotel.dao.HotelMemberCollectDao;
import io.renren.modules.hotel.entity.HotelMemberCollectEntity;
import io.renren.modules.hotel.form.AddCollectForm;
import io.renren.modules.hotel.service.HotelMemberCollectService;
import io.renren.modules.hotel.vo.CollectItemVo;

@Service("hotelMemberCollectService")
public class HotelMemberCollectServiceImpl extends ServiceImpl<HotelMemberCollectDao, HotelMemberCollectEntity> implements HotelMemberCollectService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelMemberCollectEntity> page = this.page(new Query<HotelMemberCollectEntity>().getPage(params), new QueryWrapper<HotelMemberCollectEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addCollcet(Long userId, AddCollectForm addCollectForm) {
		HotelMemberCollectEntity collectEntity = baseMapper.selectOne(Wrappers.<HotelMemberCollectEntity>lambdaQuery().eq(HotelMemberCollectEntity::getBizId, addCollectForm.getBizId()).eq(HotelMemberCollectEntity::getBizType, addCollectForm.getBizType()).eq(HotelMemberCollectEntity::getUserId, userId));
		if (null == collectEntity) {
			collectEntity = new HotelMemberCollectEntity();
			BeanUtil.copyProperties(addCollectForm, collectEntity);
			collectEntity.setUserId(userId);
			baseMapper.insert(collectEntity);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delCollect(Long userId, AddCollectForm addCollectForm) {
		int result = baseMapper.delete(Wrappers.<HotelMemberCollectEntity>lambdaQuery().eq(HotelMemberCollectEntity::getBizId, addCollectForm.getBizId()).eq(HotelMemberCollectEntity::getBizType, addCollectForm.getBizType()).eq(HotelMemberCollectEntity::getUserId, userId));
		if (result < 1) {
			throw new RRException("取消收藏失败");
		}
	}

	@Override
	public Page<CollectItemVo> collectList(Page<CollectItemVo> page, Long userId, int type) {
		Page<CollectItemVo> pageResult = baseMapper.hotelcollectPageList(page);
		return pageResult;
	}

}