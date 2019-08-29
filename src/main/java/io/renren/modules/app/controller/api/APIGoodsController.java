/**
 * @Auther: taoz
 * @Date: 14/06/2019 12:09
 * @Description:
 */
package io.renren.modules.app.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.dto.GoodsDto;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.vo.GoodsDetailVo;
import io.renren.modules.app.vo.GoodsVo;

@RestController
@RequestMapping("app/goods")
public class APIGoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品列表
     *
     * @return
     */
    @GetMapping("/list")
    public R list(Page<GoodsVo> page, GoodsDto params) {
        Page<GoodsVo> goodsListPage = goodsService.goodsList(page,params);
        return R.ok().put("data",goodsListPage);
    }

    /**
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/detail")
    public R detail(@RequestParam(name = "id") Integer goodsId){
        GoodsDetailVo goodsDetail = goodsService.goodsDetail(goodsId,null);
        return R.ok().put("data",goodsDetail);
    }
}
