package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelSellerEntity;
import io.renren.modules.hotel.vo.HotelInfo;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import io.renren.modules.hotel.vo.HotelSearchVo;

import java.util.Map;

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

	/**
	 * 酒店列表
	 * 
	 * @param userId
	 * @param params
	 * @param page
	 * @return
	 */
	Page<HotelItemVo> hotelPage(Long userId, HotelSearchCondition params, Page<HotelItemVo> page);

	/**
	 * 酒店搜索
	 * 
	 * @param kw
	 * @param page
	 * @param limit
	 * @return
	 */
	Page<HotelSearchVo> search(String kw, Page<HotelSearchVo> page);
}
