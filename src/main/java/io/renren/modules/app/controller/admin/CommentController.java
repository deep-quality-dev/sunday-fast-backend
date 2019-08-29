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
import io.renren.modules.app.entity.CommentEntity;
import io.renren.modules.app.service.CommentService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:comment:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = commentService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:comment:info")
	public R info(@PathVariable("id") Integer id) {
		CommentEntity comment = commentService.getById(id);

		return R.ok().put("comment", comment);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:comment:save")
	public R save(@RequestBody CommentEntity comment) {
		commentService.save(comment);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:comment:update")
	public R update(@RequestBody CommentEntity comment) {
		commentService.updateById(comment);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:comment:delete")
	public R delete(@RequestBody Integer[] ids) {
		commentService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
