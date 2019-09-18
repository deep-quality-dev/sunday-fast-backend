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

import io.renren.modules.hotel.entity.HotelProtocolEntity;
import io.renren.modules.hotel.service.HotelProtocolService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-09-18 20:38:22
 */
@RestController
@RequestMapping("hotel/hotelprotocol")
public class HotelProtocolController {
    @Autowired
    private HotelProtocolService hotelProtocolService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hotelprotocol:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelProtocolService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hotelprotocol:info")
    public R info(@PathVariable("id") Long id){
		HotelProtocolEntity hotelProtocol = hotelProtocolService.getById(id);

        return R.ok().put("hotelProtocol", hotelProtocol);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hotelprotocol:save")
    public R save(@RequestBody HotelProtocolEntity hotelProtocol){
		hotelProtocolService.save(hotelProtocol);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hotelprotocol:update")
    public R update(@RequestBody HotelProtocolEntity hotelProtocol){
		hotelProtocolService.updateById(hotelProtocol);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hotelprotocol:delete")
    public R delete(@RequestBody Long[] ids){
		hotelProtocolService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
