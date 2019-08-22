package io.renren.modules.hotel.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.hotel.service.HotelWalletService;
import io.renren.modules.hotel.vo.WalletDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "钱包", tags = { "钱包接口" })
@RestController
@RequestMapping("/hotel/wallet")
public class HotelWalletAPI {

	@Autowired
	private HotelWalletService hotelWalletService;

	@Login
	@ApiOperation("钱包数据")
	@GetMapping("/walletData")
	public R walletData(@RequestAttribute("userId") Long userId) {
		WalletDataVo walletDataVo = hotelWalletService.walletData(userId);
		return R.ok(walletDataVo);
	}

}
