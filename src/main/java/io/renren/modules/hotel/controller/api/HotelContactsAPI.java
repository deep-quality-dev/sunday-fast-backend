package io.renren.modules.hotel.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.hotel.form.AddContactsForm;
import io.swagger.annotations.Api;

@Api(value = "酒店联系人接口", tags = { "酒店联系人接口" })
@RestController
@RequestMapping("/hotel/contacts")
public class HotelContactsAPI {

	/**
	 * 添加联系人
	 * @param contactsForm
	 * @return
	 */
	@PostMapping
	public R add(@RequestBody AddContactsForm contactsForm) {

		return R.ok();
	}

	public R del() {

		return R.ok();
	}

	public R upd() {

		return R.ok();
	}

	public R page() {

		return R.ok();
	}
}
