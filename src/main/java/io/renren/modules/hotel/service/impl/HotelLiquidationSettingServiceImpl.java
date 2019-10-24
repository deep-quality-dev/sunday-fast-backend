package io.renren.modules.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.hotel.dao.HotelLiquidationSettingDao;
import io.renren.modules.hotel.entity.HotelLiquidationSettingEntity;
import io.renren.modules.hotel.service.HotelLiquidationSettingService;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;

@Service("hotelLiquidationSettingService")
public class HotelLiquidationSettingServiceImpl extends ServiceImpl<HotelLiquidationSettingDao, HotelLiquidationSettingEntity> implements HotelLiquidationSettingService {

	@Autowired
	private ScheduleJobService scheduleJobService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<HotelLiquidationSettingEntity> page = this.page(new Query<HotelLiquidationSettingEntity>().getPage(params), new QueryWrapper<HotelLiquidationSettingEntity>());

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveLiquidationSetting(HotelLiquidationSettingEntity hotelLiquidationSettingEntity) {
		ValidatorUtils.validateEntity(hotelLiquidationSettingEntity);
		String jobKey = "liquidationTask";
		ScheduleJobEntity scheduleJob = scheduleJobService.getOne(Wrappers.<ScheduleJobEntity>lambdaQuery().eq(ScheduleJobEntity::getBeanName, jobKey));
		if (null == scheduleJob) {
			scheduleJob = new ScheduleJobEntity();
		}
		if (hotelLiquidationSettingEntity.getType() == 1) { // 周
			String cronExpression = "0 00 00 ? * MON";
			scheduleJob.setCronExpression(cronExpression);
		}
		if (hotelLiquidationSettingEntity.getType() == 2) {// 月
			String cronExpression = "0 00 00 L * ?";
			scheduleJob.setCronExpression(cronExpression);
		}
		scheduleJob.setCreateTime(DateUtil.date());
		scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
		scheduleJob.setRemark("商家结算任务");
		scheduleJobService.saveOrUpdate(scheduleJob);
		this.saveOrUpdate(hotelLiquidationSettingEntity);
	}

}