package io.renren.modules.job.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.renren.modules.hotel.service.HotelOrderService;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单自动离店
 * 
 * @author taoz
 *
 */
@Slf4j
@Component("checkOutOrderTask")
public class CheckOutOrderTask implements ITask {
	@Autowired
	private HotelOrderService hotelOrderService;

	@Override
	public void run(String params) {
		log.debug("自动离店");
		hotelOrderService.orderAutoCheckOut();

	}

}
