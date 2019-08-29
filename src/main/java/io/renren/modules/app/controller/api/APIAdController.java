/**
 * @Auther: taoz
 * @Date: 17/06/2019 12:27
 * @Description:
 */
package io.renren.modules.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.R;
import io.renren.modules.app.entity.AdEntity;
import io.renren.modules.app.service.AdService;

/**
 * 广告
 */
@RestController
@RequestMapping("app/ad")
public class APIAdController {

    @Autowired
    private AdService adService;


    @GetMapping("/list/{type}")
    public R list(@PathVariable int type) {
        List<AdEntity> ads = adService.list(Wrappers.<AdEntity>lambdaQuery().eq(AdEntity::getType, type));
        return R.ok().put("data", ads);
    }

}
