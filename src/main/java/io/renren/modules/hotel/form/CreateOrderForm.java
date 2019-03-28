package io.renren.modules.hotel.form;

import java.util.Date;

import lombok.Data;

/**
 * 创建房间订单
 * 
 * @author taoz
 *
 */
@Data
public class CreateOrderForm {

	// 房间ID
	private Long roomId;

	// 商家ID
	private Long sellerId;
	// 金额ID
	private Long moneyId;
	// 用户ID
	private Long userId;
	// 房间名称
	private String roomName;
	// 入住时间
	private String checkInDate;

	// 离店时间
	private String checkOutDate;

	// 入住天数
	private long checkInDay;

	// 房间数量
	private int roomNum;

	// 入住人
	private String checkInPerson;

	// 联系电话
	private String mobile;

	// 总金额分
	private Integer totalAmountFen;
	// 到店时间
	private Date ddTime;

	// 优惠券ID
	private Long couponId;

	// 经纬度
	private String coordinates;

}
