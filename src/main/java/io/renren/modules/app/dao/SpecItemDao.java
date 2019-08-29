package io.renren.modules.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.modules.app.entity.SpecItemEntity;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-23 13:59:07
 */
@Mapper
public interface SpecItemDao extends BaseMapper<SpecItemEntity> {

    /**
     * 获取商品柜子值列表
     * @param goodsId
     * @param specId
     * @return
     */
    List<SpecItemEntity> getSpecListByGoodsId(@Param("goodsId") Integer goodsId, @Param("specId") Integer specId);
}
