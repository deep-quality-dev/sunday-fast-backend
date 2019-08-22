package io.renren.modules.hotel.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.form.AddContactsForm;
import io.renren.modules.hotel.service.HotelContactsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店联系人接口", tags = { "酒店联系人接口" })
@RestController
@RequestMapping("/hotel/contacts")
public class HotelContactsAPI {

	@Autowired
	private HotelContactsService hotelContactsService;

	/**
	 * 添加联系人
	 * 
	 * @param contactsForm
	 * @return
	 */
	@Login
	@PostMapping
	@ApiOperation("添加联系人")
	public R add(@RequestAttribute("userId") Long userId, @RequestBody AddContactsForm contactsForm) {
		hotelContactsService.addContacts(userId, contactsForm);
		return R.ok();
	}

	@Login
	@DeleteMapping("/{id}")
	@ApiOperation("删除联系人")
	public R del(@RequestAttribute("userId") Long userId, @PathVariable Long id) {
		hotelContactsService.delContacts(userId, id);
		return R.ok();
	}

	@Login
	@PutMapping
	@ApiOperation("修改联系人列表")
	public R upd(@RequestAttribute("userId") Long userId, @RequestBody AddContactsForm contactsForm) {
		hotelContactsService.updContacts(userId, contactsForm);
		return R.ok();
	}

	@PostMapping
	@Login
	@ApiOperation("添加联系人")
	public R list(@RequestAttribute("userId") Long userId) {
		List<AddContactsForm> list = hotelContactsService.contactsList(userId);
		return R.ok(list);
	}
}
