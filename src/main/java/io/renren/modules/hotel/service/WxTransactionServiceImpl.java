package io.renren.modules.hotel.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.exception.WxPayException;

import io.renren.common.exception.RRException;
import io.renren.modules.hotel.config.WxPayConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wxTransactionService")
public class WxTransactionServiceImpl implements TransactionService {

	@Override
	public void refund(Map<String, String> refundParams) {
		log.info("微信退款--start,params:{}", JSON.toJSONString(refundParams));
		String appId = refundParams.get("appId");
		String outTradeNo = refundParams.get("outTradeNo");
		WxPayRefundRequest request = new WxPayRefundRequest();
		request.setAppid(appId);
		request.setOutTradeNo(outTradeNo);
		try {
			WxPayConfiguration.getPayServices().get(appId).refund(request);
		} catch (WxPayException e) {
			throw new RRException("微信退款异常");
		}
	}

}
