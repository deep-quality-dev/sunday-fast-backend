package io.renren.modules.hotel.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.hotel.entity.HotelMemberCollectEntity;
import io.renren.modules.hotel.vo.CollectItemVo;

/**
 * 用户收藏表
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-21 21:28:42
 */
@Mapper
public interface HotelMemberCollectDao extends BaseMapper<HotelMemberCollectEntity> {

	/**
	 * 收藏列表
	 * 
	 * @param page
	 * @param type
	 * @return
	 */
	Page<CollectItemVo> hotelcollectPageList(Page<CollectItemVo> page);

}
