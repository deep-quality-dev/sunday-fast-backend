package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelWithdrawalEntity;

/**
 * 提现记录
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
public interface HotelWithdrawalService extends IService<HotelWithdrawalEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
