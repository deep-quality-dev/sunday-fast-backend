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

import io.renren.modules.hotel.entity.HotelBrandTypeEntity;
import io.renren.modules.hotel.service.HotelBrandTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-16 19:47:22
 */
@RestController
@RequestMapping("hotel/hotelbrandtype")
public class HotelBrandTypeController {
    @Autowired
    private HotelBrandTypeService hotelBrandTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hotelbrandtype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelBrandTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hotelbrandtype:info")
    public R info(@PathVariable("id") Long id){
		HotelBrandTypeEntity hotelBrandType = hotelBrandTypeService.getById(id);

        return R.ok().put("hotelBrandType", hotelBrandType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hotelbrandtype:save")
    public R save(@RequestBody HotelBrandTypeEntity hotelBrandType){
		hotelBrandTypeService.save(hotelBrandType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hotelbrandtype:update")
    public R update(@RequestBody HotelBrandTypeEntity hotelBrandType){
		hotelBrandTypeService.updateById(hotelBrandType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hotelbrandtype:delete")
    public R delete(@RequestBody Long[] ids){
		hotelBrandTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
