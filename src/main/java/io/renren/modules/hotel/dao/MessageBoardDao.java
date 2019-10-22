package io.renren.modules.hotel.dao;

import io.renren.modules.hotel.entity.MessageBoardEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言板
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-22 21:41:35
 */
@Mapper
public interface MessageBoardDao extends BaseMapper<MessageBoardEntity> {
	
}
