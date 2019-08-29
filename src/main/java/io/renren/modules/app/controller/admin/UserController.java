package io.renren.modules.app.controller.admin;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.UserService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:user:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = userService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:user:info")
	public R info(@PathVariable("id") Integer id) {
		UserEntity user = userService.getById(id);

		return R.ok().put("user", user);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:user:save")
	public R save(@RequestBody UserEntity user) {
		userService.save(user);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:user:update")
	public R update(@RequestBody UserEntity user) {
		userService.updateById(user);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:user:delete")
	public R delete(@RequestBody Integer[] ids) {
		userService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
