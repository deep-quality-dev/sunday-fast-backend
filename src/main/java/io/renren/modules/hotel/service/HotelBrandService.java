package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelBrandEntity;
import io.renren.modules.hotel.vo.HotelBrandTypeVo;
import io.renren.modules.hotel.vo.HotelBrandVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-16 19:47:22
 */
public interface HotelBrandService extends IService<HotelBrandEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 酒店类型品牌
	 * 
	 * @return
	 */
	List<HotelBrandTypeVo> hotelBrandWithType();

	/**
	 * 根据类型加载酒店品牌
	 * 
	 * @param typeId
	 * @return
	 */
	List<HotelBrandVo> loadBrandByType(Long typeId);

	/**
	 * 加载所有酒店品牌
	 * 
	 * @return
	 */
	List<HotelBrandVo> loadAllBrand();
}
