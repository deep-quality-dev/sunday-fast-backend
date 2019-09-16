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

import io.renren.modules.hotel.entity.HotelTopicEntity;
import io.renren.modules.hotel.service.HotelTopicService;
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
@RequestMapping("hotel/hoteltopic")
public class HotelTopicController {
    @Autowired
    private HotelTopicService hotelTopicService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:hoteltopic:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = hotelTopicService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:hoteltopic:info")
    public R info(@PathVariable("id") Long id){
		HotelTopicEntity hotelTopic = hotelTopicService.getById(id);

        return R.ok().put("hotelTopic", hotelTopic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:hoteltopic:save")
    public R save(@RequestBody HotelTopicEntity hotelTopic){
		hotelTopicService.save(hotelTopic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:hoteltopic:update")
    public R update(@RequestBody HotelTopicEntity hotelTopic){
		hotelTopicService.updateById(hotelTopic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:hoteltopic:delete")
    public R delete(@RequestBody Long[] ids){
		hotelTopicService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
