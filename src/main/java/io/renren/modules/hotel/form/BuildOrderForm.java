package io.renren.modules.hotel.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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

	private Long roomId;
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
	@ApiModelProperty(value = "房间数量")
	@NotNull(message = "房间数量不能为空")
	private Long checkInDay;

	// 房间数量
	@ApiModelProperty(value = "房间数量")
	@NotNull(message = "房间数量不能为空")
	private Integer roomNum;

	// 入住人
	@ApiModelProperty(value = "入住人")
	@NotBlank(message = "入住人不能为空")
	private String checkInPerson;

	// 联系电话
	@ApiModelProperty(value = "联系电话")
	@NotBlank(message = "联系电话不能为空")
	private String mobile;

	// 格式化总金额
	private String totalAmount;

	private Integer totalAmountFen;

	// 优惠金额
	private String discountsAcmount;

	// 优惠券ID
	private Long couponId;

	// 经纬度
	private String coordinates;

	private String ip;

	// 明细
	List<OrderDetail> record = new ArrayList<OrderDetail>();
}
