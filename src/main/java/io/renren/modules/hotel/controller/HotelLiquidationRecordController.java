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

import io.renren.modules.hotel.entity.HotelLiquidationRecordEntity;
import io.renren.modules.hotel.service.HotelLiquidationRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-23 17:42:18
 */
@RestController
@RequestMapping("hotel/hotelliquidationrecord")
public class HotelLiquidationRecordController {
    @Autowired
    private HotelLiquidationRecordService hotelLiquidationRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hotelliquidationrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelLiquidationRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hotelliquidationrecord:info")
    public R info(@PathVariable("id") Long id){
		HotelLiquidationRecordEntity hotelLiquidationRecord = hotelLiquidationRecordService.getById(id);

        return R.ok().put("hotelLiquidationRecord", hotelLiquidationRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hotelliquidationrecord:save")
    public R save(@RequestBody HotelLiquidationRecordEntity hotelLiquidationRecord){
		hotelLiquidationRecordService.save(hotelLiquidationRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hotelliquidationrecord:update")
    public R update(@RequestBody HotelLiquidationRecordEntity hotelLiquidationRecord){
		hotelLiquidationRecordService.updateById(hotelLiquidationRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hotelliquidationrecord:delete")
    public R delete(@RequestBody Long[] ids){
		hotelLiquidationRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
