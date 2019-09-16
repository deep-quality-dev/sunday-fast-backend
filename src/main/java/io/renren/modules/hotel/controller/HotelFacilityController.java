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

import io.renren.modules.hotel.entity.HotelFacilityEntity;
import io.renren.modules.hotel.service.HotelFacilityService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-16 20:30:49
 */
@RestController
@RequestMapping("hotel/hotelfacility")
public class HotelFacilityController {
    @Autowired
    private HotelFacilityService hotelFacilityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hotelfacility:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelFacilityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hotelfacility:info")
    public R info(@PathVariable("id") Long id){
		HotelFacilityEntity hotelFacility = hotelFacilityService.getById(id);

        return R.ok().put("hotelFacility", hotelFacility);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hotelfacility:save")
    public R save(@RequestBody HotelFacilityEntity hotelFacility){
		hotelFacilityService.save(hotelFacility);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hotelfacility:update")
    public R update(@RequestBody HotelFacilityEntity hotelFacility){
		hotelFacilityService.updateById(hotelFacility);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hotelfacility:delete")
    public R delete(@RequestBody Long[] ids){
		hotelFacilityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
