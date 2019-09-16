package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelFacilityEntity;
import io.renren.modules.hotel.vo.FacilityVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-16 20:30:49
 */
public interface HotelFacilityService extends IService<HotelFacilityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 
     * @param type 
     * @return
     */
	List<FacilityVo> hotelFacility(int type);
}

