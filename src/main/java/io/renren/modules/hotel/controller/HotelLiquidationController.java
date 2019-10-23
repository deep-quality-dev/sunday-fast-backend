package io.renren.modules.hotel.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.hotel.entity.HotelLiquidationEntity;
import io.renren.modules.hotel.service.HotelLiquidationService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:19
 */
@RestController
@RequestMapping("hotel/hotelliquidation")
public class HotelLiquidationController {
    @Autowired
    private HotelLiquidationService hotelLiquidationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hotelliquidation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelLiquidationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hotelliquidation:info")
    public R info(@PathVariable("id") Long id){
		HotelLiquidationEntity hotelLiquidation = hotelLiquidationService.getById(id);

        return R.ok().put("hotelLiquidation", hotelLiquidation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hotelliquidation:save")
    public R save(@RequestBody HotelLiquidationEntity hotelLiquidation){
		hotelLiquidationService.save(hotelLiquidation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hotelliquidation:update")
    public R update(@RequestBody HotelLiquidationEntity hotelLiquidation){
		hotelLiquidationService.updateById(hotelLiquidation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hotelliquidation:delete")
    public R delete(@RequestBody Long[] ids){
		hotelLiquidationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
