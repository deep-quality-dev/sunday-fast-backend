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

import io.renren.modules.hotel.entity.MessageBoardEntity;
import io.renren.modules.hotel.service.MessageBoardService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 留言板
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-10-22 21:41:35
 */
@RestController
@RequestMapping("hotel/messageboard")
public class MessageBoardController {
    @Autowired
    private MessageBoardService messageBoardService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("hotel:messageboard:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageBoardService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("hotel:messageboard:info")
    public R info(@PathVariable("id") Integer id){
		MessageBoardEntity messageBoard = messageBoardService.getById(id);

        return R.ok().put("messageBoard", messageBoard);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("hotel:messageboard:save")
    public R save(@RequestBody MessageBoardEntity messageBoard){
		messageBoardService.save(messageBoard);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("hotel:messageboard:update")
    public R update(@RequestBody MessageBoardEntity messageBoard){
		messageBoardService.updateById(messageBoard);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("hotel:messageboard:delete")
    public R delete(@RequestBody Integer[] ids){
		messageBoardService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
