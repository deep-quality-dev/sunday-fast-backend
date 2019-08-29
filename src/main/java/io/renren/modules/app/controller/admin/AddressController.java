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
import io.renren.modules.app.entity.AddressEntity;
import io.renren.modules.app.service.AddressService;

/**
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-05-20 16:07:41
 */
@RestController
@RequestMapping("app/address")
public class AddressController {
	@Autowired
	private AddressService addressService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("app:address:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = addressService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("app:address:info")
	public R info(@PathVariable("id") Integer id) {
		AddressEntity address = addressService.getById(id);

		return R.ok().put("address", address);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("app:address:save")
	public R save(@RequestBody AddressEntity address) {
		addressService.save(address);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("app:address:update")
	public R update(@RequestBody AddressEntity address) {
		addressService.updateById(address);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("app:address:delete")
	public R delete(@RequestBody Integer[] ids) {
		addressService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
