package io.renren.modules.hotel.dao;

import io.renren.modules.hotel.entity.AssessTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-10 16:26:08
 */
@Mapper
public interface AssessTagDao extends BaseMapper<AssessTagEntity> {

	List<AssessTagEntity> hotelTags(Long sellerId);
	
}
