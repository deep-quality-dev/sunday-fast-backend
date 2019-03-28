package io.renren.modules.hotel.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

import io.renren.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wxTransactionService")
public class WxTransactionServiceImpl implements TransactionService {

	@Autowired
	private WxPayService wxService;

	@Override
	public void refund(Map<String, String> refundParams) {
		log.info("微信退款--start,params:{}", JSON.toJSONString(refundParams));
		String appId = refundParams.get("appId");
		String outTradeNo = refundParams.get("outTradeNo");
		WxPayRefundRequest request = new WxPayRefundRequest();
		request.setAppid(appId);
		request.setOutTradeNo(outTradeNo);
		try {
			wxService.refund(request);
		} catch (WxPayException e) {
			throw new RRException("微信退款异常");
		}
	}

}
