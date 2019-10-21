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

import io.renren.modules.hotel.entity.BankEntity;
import io.renren.modules.hotel.service.BankService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-17 15:41:25
 */
@RestController
@RequestMapping("hotel/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:bank:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bankService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:bank:info")
    public R info(@PathVariable("id") Long id){
		BankEntity bank = bankService.getById(id);

        return R.ok().put("bank", bank);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:bank:save")
    public R save(@RequestBody BankEntity bank){
		bankService.save(bank);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:bank:update")
    public R update(@RequestBody BankEntity bank){
		bankService.updateById(bank);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:bank:delete")
    public R delete(@RequestBody Long[] ids){
		bankService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
