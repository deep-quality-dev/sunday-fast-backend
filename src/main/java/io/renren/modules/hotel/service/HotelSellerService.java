package io.renren.modules.hotel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.vo.HotelInfo;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
public interface HotelSellerService extends IService<HotelSellerEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 获取酒店信息
	 * 
	 * @param sellerId
	 * @return
	 */
	HotelInfo sellerInfo(Long sellerId);
}
