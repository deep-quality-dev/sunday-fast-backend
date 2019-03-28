package io.renren.modules.hotel.vo;

import java.math.BigDecimal;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;

/**
 * 房型价格
 * 
 * @author taoz
 *
 */
@Data
public class RoomMoneyVo {
	
	private Long id;

	private String name;

	private String amount;

	private int hasRoom = 1;

	public void setAmount(BigDecimal amount) {
		this.amount = NumberUtil.decimalFormat("0.00", amount.doubleValue());
	}
}
