package io.renren.modules.hotel.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;

import cn.hutool.core.util.IdUtil;
import io.renren.common.exception.RRException;
import io.renren.modules.hotel.config.WxPayConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wxTransactionService")
public class WxTransactionServiceImpl implements TransactionService {

	@Override
	public void refund(Map<String, Object> refundParams) {
		log.info("微信退款--start,params:{}", JSON.toJSONString(refundParams));
		String appId = String.valueOf(refundParams.get("appId"));
		String outTradeNo = String.valueOf(refundParams.get("outTradeNo"));
		Integer totalFee = Integer.valueOf(refundParams.get("totalFee").toString());
		Integer refundFee = Integer.valueOf(refundParams.get("refundFee").toString());
		WxPayRefundRequest request = new WxPayRefundRequest();
		request.setAppid(appId);
		request.setOutTradeNo(outTradeNo);
		request.setTotalFee(totalFee);
		request.setRefundFee(refundFee);
		request.setOutRefundNo(IdUtil.simpleUUID());
		request.setNotifyUrl("https://hotelapi.xqtinfo.cn/pay/" + appId + "/notify/refund");
		try {
			WxPayRefundResult payRefundResult = WxPayConfiguration.getPayServices().get(appId).refund(request);
			log.info("payRefundResult:{}", JSON.toJSONString(payRefundResult));
		} catch (WxPayException e) {
			e.printStackTrace();
			throw new RRException("微信退款异常");
		}
	}

}
