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

import io.renren.modules.hotel.entity.HotelLiquidationSettingEntity;
import io.renren.modules.hotel.service.HotelLiquidationSettingService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 结算设置
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:19
 */
@RestController
@RequestMapping("hotel/hotelliquidationsetting")
public class HotelLiquidationSettingController {
    @Autowired
    private HotelLiquidationSettingService hotelLiquidationSettingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hotelliquidationsetting:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelLiquidationSettingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hotelliquidationsetting:info")
    public R info(@PathVariable("id") Long id){
		HotelLiquidationSettingEntity hotelLiquidationSetting = hotelLiquidationSettingService.getById(id);

        return R.ok().put("hotelLiquidationSetting", hotelLiquidationSetting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hotelliquidationsetting:save")
    public R save(@RequestBody HotelLiquidationSettingEntity hotelLiquidationSetting){
		hotelLiquidationSettingService.save(hotelLiquidationSetting);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hotelliquidationsetting:update")
    public R update(@RequestBody HotelLiquidationSettingEntity hotelLiquidationSetting){
		hotelLiquidationSettingService.updateById(hotelLiquidationSetting);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hotelliquidationsetting:delete")
    public R delete(@RequestBody Long[] ids){
		hotelLiquidationSettingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
