package io.renren.modules.app.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.renren.common.utils.R;
import io.renren.modules.app.entity.UploadGroupEntity;
import io.renren.modules.app.service.UploadGroupService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-06-05 15:33:58
 */
@RestController
@RequestMapping("admin/uploadgroup")
public class UploadGroupController {
	@Autowired
	private UploadGroupService uploadGroupService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:uploadgroup:list")
	public R list(@RequestParam Map<String, Object> params) {
		List<UploadGroupEntity> groupEntities = uploadGroupService.list(Wrappers.<UploadGroupEntity>query().lambda().orderByAsc(UploadGroupEntity::getSort));
		return R.ok().put("data", groupEntities);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:uploadgroup:info")
	public R info(@PathVariable("id") Integer id) {
		UploadGroupEntity uploadGroup = uploadGroupService.getById(id);

		return R.ok().put("uploadGroup", uploadGroup);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:uploadgroup:save")
	public R save(@RequestBody UploadGroupEntity uploadGroup) {
		uploadGroupService.save(uploadGroup);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:uploadgroup:update")
	public R update(@RequestBody UploadGroupEntity uploadGroup) {
		uploadGroupService.updateById(uploadGroup);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:uploadgroup:delete")
	public R delete(@RequestBody Integer[] ids) {
		uploadGroupService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
