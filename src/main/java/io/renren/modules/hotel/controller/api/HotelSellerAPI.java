package io.renren.modules.hotel.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.vo.HotelItemVo;
import io.renren.modules.hotel.vo.HotelSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.common.utils.R;
import io.renren.modules.hotel.service.HotelSellerService;
import io.renren.modules.hotel.vo.HotelInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * 酒店商家
 *
 * @author taoz
 */
@Api(value = "酒店商家接口", tags = {"酒店商家接口"})
@RestController
@RequestMapping("/{appId}/hotel/seller")
public class HotelSellerAPI extends BaseController {

    @Autowired
    private HotelSellerService hotelSellerService;

    /**
     * 酒店信息
     *
     * @param appId
     */
    @ApiOperation("酒店信息")
    @GetMapping("/info")
    public R info(@PathVariable String appId) {
        HotelInfo hotelInfo = hotelSellerService.sellerInfo(sellerId(appId));
        return R.ok().put("data", hotelInfo);
    }


    /**
     * 酒店列表
     *
     * @param page
     * @param params
     * @return
     */
    @ApiOperation("酒店列表")
    @GetMapping("/page")
    public R page(@ModelAttribute HotelSearchCondition params, Page page) {
        Page<HotelItemVo> pageResult = hotelSellerService.hotelPage(1L, params, page);
        return R.ok().put("data", pageResult);
    }
}
