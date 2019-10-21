package io.renren.modules.hotel.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.hotel.entity.HotelWithdrawalEntity;
import io.renren.modules.hotel.form.WithdrawalApplyForm;
import io.renren.modules.hotel.service.HotelWithdrawalService;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 提现记录
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:33
 */
@RestController
@RequestMapping("hotel/hotelwithdrawal")
public class HotelWithdrawalController extends AbstractController {
	@Autowired
	private HotelWithdrawalService hotelWithdrawalService;

	/**
	 * 提现申请数据
	 * 
	 * @return
	 */
	@GetMapping("/withdrawalApplyData")
	@RequiresPermissions("hotel:hotelwithdrawal:withdrawalapplydata")
	public R withdrawalApplyData() {
		BigDecimal amount = hotelWithdrawalService.withdrawalApplyData(getSellerId());
		return R.ok().put("data", amount);
	}

	/**
	 * 提交提现申请
	 * 
	 * @return
	 */
	@PostMapping("/withdrawalApply")
	@RequiresPermissions("hotel:hotelwithdrawal:withdrawalapply")
	public R withdrawalApply(@RequestBody WithdrawalApplyForm withdrawalApplyForm) {
		hotelWithdrawalService.withdrawalApply(getSellerId(), withdrawalApplyForm);
		return R.ok();
	}

	/**
	 * 提现审核
	 * 
	 * @return
	 */
	@RequiresPermissions("hotel:hotelwithdrawal:withdrawalapplyaudit")
	@PostMapping("/handlerWithdrawalApply/{id}")
	public R withdrawalApplyAudit(@PathVariable Long id) {
		hotelWithdrawalService.withdrawalApplyAudit(id);
		return R.ok();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelwithdrawal:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = hotelWithdrawalService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelwithdrawal:info")
	public R info(@PathVariable("id") Integer id) {
		HotelWithdrawalEntity hotelWithdrawal = hotelWithdrawalService.getById(id);

		return R.ok().put("hotelWithdrawal", hotelWithdrawal);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelwithdrawal:save")
	public R save(@RequestBody HotelWithdrawalEntity hotelWithdrawal) {
		hotelWithdrawalService.save(hotelWithdrawal);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelwithdrawal:update")
	public R update(@RequestBody HotelWithdrawalEntity hotelWithdrawal) {
		hotelWithdrawalService.updateById(hotelWithdrawal);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelwithdrawal:delete")
	public R delete(@RequestBody Integer[] ids) {
		hotelWithdrawalService.removeByIds(Arrays.asList(ids));

		return R.ok();
	}

}
