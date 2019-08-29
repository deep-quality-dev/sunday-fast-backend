package io.renren.modules.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.vo.GoodsVo;
import io.renren.modules.app.vo.OrderVo;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

	/**
	 * 订单分页
	 * @param page
	 * @param status
	 * @param userId
	 * @return
	 */
	Page<OrderVo> orderList(Page<GoodsVo> page, Integer status, Integer userId);

}
