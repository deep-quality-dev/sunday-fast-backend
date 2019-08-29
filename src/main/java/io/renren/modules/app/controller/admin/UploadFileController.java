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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.common.utils.R;
import io.renren.modules.app.entity.UploadFileEntity;
import io.renren.modules.app.service.UploadFileService;

/**
 * 
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-06-05 15:33:58
 */
@RestController
@RequestMapping("admin/uploadfile")
public class UploadFileController {
	@Autowired
	private UploadFileService uploadFileService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:uploadfile:list")
	public R list(Page<UploadFileEntity> page, @RequestParam Map<String, Object> params) {
		return R.ok().put("data", uploadFileService.queryPage(page, params));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:uploadfile:info")
	public R info(@PathVariable("id") Integer id) {
		UploadFileEntity uploadFile = uploadFileService.getById(id);

		return R.ok().put("uploadFile", uploadFile);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:uploadfile:save")
	public R save(@RequestBody UploadFileEntity uploadFile) {
		uploadFileService.save(uploadFile);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:uploadfile:update")
	public R update(@RequestBody UploadFileEntity uploadFile) {
		uploadFileService.updateById(uploadFile);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:uploadfile:delete")
	public R delete(@RequestBody Integer[] ids) {
		uploadFileService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
