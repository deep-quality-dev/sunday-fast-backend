package io.renren.modules.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.app.entity.OrderGoodsEntity;
import io.renren.modules.app.vo.OrderItemVo;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Mapper
public interface OrderGoodsDao extends BaseMapper<OrderGoodsEntity> {

	/**
	 * 获取订单商品
	 * @param id
	 * @return
	 */
	List<OrderItemVo> getOrderGoods(@Param("orderId")Integer orderId);

}
