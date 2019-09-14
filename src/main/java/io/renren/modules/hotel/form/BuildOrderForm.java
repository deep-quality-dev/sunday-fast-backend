package io.renren.modules.hotel.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.renren.modules.hotel.vo.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 构建订单信息
 * 
 * @author taoz
 *
 */
@Data
@ApiModel(value = "提交订单表单")
public class BuildOrderForm {

	@ApiModelProperty(value = "房间ID")
	@NotNull(message = "房间ID不能为空")
	private Long roomId;

	@ApiModelProperty(value = "金额ID")
	@NotNull(message = "金额ID不能为空")
	private Long moneyId;

	// 房间名称
	private String roomName;

	// 入住时间
	@ApiModelProperty(value = "入住时间")
	@NotBlank(message = "入住时间不能为空")
	private String checkInDate;

	// 离店时间
	@ApiModelProperty(value = "离店时间")
	@NotBlank(message = "离店时间不能为空")
	private String checkOutDate;

	// 房间数量
	@ApiModelProperty(value = "入住天数")
	private Long checkInDay;

	// 房间数量
	@ApiModelProperty(value = "房间数量")
	private Integer roomNum;

	@ApiModelProperty(value = "联系人ID")
	@NotNull(message = "联系人ID")
	private Long contactsId;
	// 格式化总金额
	private String totalAmount;

	private Integer totalAmountFen;

	// 优惠金额
	private String discountsAcmount;

	// 优惠券ID
	@ApiModelProperty(value = "优惠券ID")
	private Long couponId;

	// 经纬度
	private String coordinates;

	private String ip;

	private String hotelAddress;

	private String sellerName;

	// 小程序消息场景
	private String formId;

	/**
	 * 订单是否是需要预付
	 */
	private int prepay;

	// 支付方式
	private String payMethod;

	// 用户余额
	private BigDecimal memberBalance;

	// 用户积分
	private BigDecimal memberIntegral;

	// 支付积分
	private BigDecimal payIntegral;

	// 明细
	List<OrderDetail> record = new ArrayList<OrderDetail>();
}
