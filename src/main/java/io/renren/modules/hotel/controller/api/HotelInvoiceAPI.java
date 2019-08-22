package io.renren.modules.hotel.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.form.AddInvoiceForm;
import io.renren.modules.hotel.service.HotelInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店发票接口", tags = { "酒店发票接口" })
@RestController
@RequestMapping("/hotel/invoice")
public class HotelInvoiceAPI {

	@Autowired
	private HotelInvoiceService hotelInvoiceService;

	@Login
	@PostMapping
	@ApiOperation("添加发票")
	public R add(@RequestAttribute("userId") Long userId, @RequestBody AddInvoiceForm addInvoiceForm) {
		hotelInvoiceService.addInvoice(userId, addInvoiceForm);
		return R.ok();
	}

	@Login
	@DeleteMapping("/{id}")
	@ApiOperation("删除发票")
	public R del(@RequestAttribute("userId") Long userId, @PathVariable Long id) {
		hotelInvoiceService.delInvoice(userId, id);
		return R.ok();
	}

	@Login
	@PutMapping
	@ApiOperation("修改发票")
	public R upd(@RequestAttribute("userId") Long userId, @RequestBody AddInvoiceForm addInvoiceForm) {
		hotelInvoiceService.updInvoice(userId, addInvoiceForm);
		return R.ok();
	}

	@Login
	@GetMapping
	@ApiOperation("发票列表")
	public R list(@RequestAttribute("userId") Long userId) {
		List<AddInvoiceForm> addInvoiceForms = hotelInvoiceService.invoiceList(userId);
		return R.ok(addInvoiceForms);
	}
}
