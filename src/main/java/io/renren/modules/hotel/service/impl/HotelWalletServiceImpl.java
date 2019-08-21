package io.renren.modules.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.hotel.service.HotelCouponsService;
import io.renren.modules.hotel.service.HotelWalletService;
import io.renren.modules.hotel.vo.WalletDataVo;

@Service
public class HotelWalletServiceImpl implements HotelWalletService {

	@Autowired
	private HotelCouponsService hotelCouponsService;

	@Override
	public WalletDataVo walletData(Long userId) {
		WalletDataVo walletDataVo = hotelCouponsService.walletData(userId);
		return walletDataVo;
	}

}
