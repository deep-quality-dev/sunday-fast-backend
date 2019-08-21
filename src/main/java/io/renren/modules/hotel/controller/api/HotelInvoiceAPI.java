package io.renren.modules.hotel.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.swagger.annotations.Api;

@Api(value = "酒店发票接口", tags = { "酒店发票接口" })
@RestController
@RequestMapping("/hotel/invoice")
public class HotelInvoiceAPI {

	public R add() {

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
