package io.renren.modules.hotel.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.AssessTagEntity;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-10 16:26:08
 */
public interface AssessTagService extends IService<AssessTagEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 酒店评论tag
	 * 
	 * @param sellerId
	 * @return
	 */
	List<AssessTagEntity> hotelTags(Long sellerId);
}
