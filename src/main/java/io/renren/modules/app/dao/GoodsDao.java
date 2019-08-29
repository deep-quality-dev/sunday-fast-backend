package io.renren.modules.app.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.entity.GoodsEntity;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {

	/**
	 * 商品分页
	 * 
	 * @param page
	 * @param goodsDto
	 * @return
	 */
	IPage getGoodsPage(Page page, @Param("query") GoodsDto goodsDto);

	/**
	 * 商品列表
	 * @param page
	 * @param params
	 * @return
	 */
    Page goodsList(Page page, GoodsDto params);
}
