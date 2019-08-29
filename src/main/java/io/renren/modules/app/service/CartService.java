package io.renren.modules.app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.CartEntity;
import io.renren.modules.app.form.GoodsSku;
import io.renren.modules.app.vo.CartVo;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
public interface CartService extends IService<CartEntity> {

	PageUtils queryPage(Map<String, Object> params);

    /**
     *
     * @param goodsSku 商品数据
     * @param memberId
     * @param num
     */
    void intoCart(GoodsSku goodsSku,int num, int memberId);

    /**
     * 移除购物车商品
     * @param cartId
     * @param i
     */
    void delCart(Integer cartId, int i);

    /**
     * 购物车列表
     * @param memberId
     * @return
     */
    List<CartVo> cartList(Integer memberId);
}
