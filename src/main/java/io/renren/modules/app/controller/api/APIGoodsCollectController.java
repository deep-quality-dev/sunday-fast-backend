package io.renren.modules.app.controller.api;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.service.GoodsCollectService;

@RestController
@RequestMapping("app/goods/collect")
public class APIGoodsCollectController {

    @Autowired
    private GoodsCollectService goodsCollectService;

    /**
     * 添加商品搜=收藏
     *
     * @param params
     * @param userId
     * @return
     */
    @Login
    @PostMapping
    public R add(@RequestBody Map<String, Object> params, @RequestAttribute("userId") Integer userId) {
        goodsCollectService.addGoodsCollect(Integer.valueOf(params.get("goodsId").toString()), userId);
        return R.ok();
    }

    /**
     * 查询商品收藏
     *
     * @param goodsId
     * @param userId
     * @return
     */
    @Login
    @GetMapping("/getGoodsCollect")
    public R getGoodsCollect(Integer goodsId, @RequestAttribute("userId") Integer userId) {
        boolean result = goodsCollectService.getGoodsCollect(goodsId, userId);
        return R.ok().put("data", result);
    }

    /**
     * 删除收藏
     *
     * @param goodsId
     * @param userId
     * @return
     */
    @Login
    @DeleteMapping
    public R del(Integer goodsId, @RequestAttribute("userId") Integer userId) {
        goodsCollectService.delGoodsCollect(goodsId, userId);
        return R.ok();
    }
}
