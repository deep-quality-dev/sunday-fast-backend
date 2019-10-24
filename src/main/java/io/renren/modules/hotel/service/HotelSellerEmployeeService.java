package io.renren.modules.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelSellerEmployeeEntity;

import java.util.Map;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 22:32:03
 */
public interface HotelSellerEmployeeService extends IService<HotelSellerEmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存员工
     * @param hotelSellerEmployee
     */
	void saveEmployee(HotelSellerEmployeeEntity hotelSellerEmployee,Long userId);

	/**
	 * 修改员工
	 * @param hotelSellerEmployee
	 */
	void updateEmployee(HotelSellerEmployeeEntity hotelSellerEmployee);
}

