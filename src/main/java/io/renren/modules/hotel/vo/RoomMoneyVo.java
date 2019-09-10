package io.renren.modules.hotel.vo;

import java.math.BigDecimal;
import java.util.List;

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

	private Long id; // ID

	private String name; // 价格名字

	private String amount; // 价格

	private int hasRoom = 1; // 是否有房 1-有 0-无

	private int vipPrice;

	private int prepay;

	/**
	 * 会员价列表
	 */
	private List<RoomVipMoneyVo> vipPriceList;

	public void setAmount(BigDecimal amount) {
		this.amount = NumberUtil.decimalFormat("0.00", amount.doubleValue());
	}
}
