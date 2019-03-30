package io.renren.modules.job.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.renren.modules.hotel.service.HotelOrderService;
import io.renren.modules.job.task.ITask;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("cancelOrderTask")
public class CancelOrderTask implements ITask{
	
	@Autowired
	private HotelOrderService hotelOrderService;

	@Override
	@Transactional
	public void run(String params) {
		log.info("cancle orders");
		hotelOrderService.updateOrder2Cancel();
	}

}
