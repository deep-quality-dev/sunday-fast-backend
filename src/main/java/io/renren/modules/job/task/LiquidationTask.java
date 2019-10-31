package io.renren.modules.job.task;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("liquidationTask")
public class LiquidationTask implements ITask {

	@Override
	public void run(String params) {
		log.info("liquidationTask start .... ");

	}

}
