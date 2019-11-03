package io.renren.modules.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * 
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-26 12:40:40
 */
@Data
@TableName("t_hotel_order")
public class HotelOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 房ID
	 */
	private Long roomId;

	/**
	 * 金额ID
	 */
	private Long moneyId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 优惠券ID
	 */
	private Long couponsId;

	/**
	 * 早餐券
	 */
	private Long breakCouponId;

	/**
	 * 免房券
	 */
	private Long freeRoomCouponId;
	/**
	 * 发票ID
	 */
	private Long invoiceId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 商家名字
	 */
	private String sellerName;
	/**
	 * 商家地址
	 */
	private String sellerAddress;
	/**
	 * 经纬度
	 */
	private String coordinates;
	/**
	 * 入住时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date arrivalTime;
	/**
	 * 离店时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date departureTime;
	/**
	 * 到店时间
	 */
	private String ddTime;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 房间数量
	 */
	private Integer num;
	/**
	 * 入住天数
	 */
	private Integer days;
	/**
	 * 房型
	 */
	private String roomType;
	/**
	 * 房间主图
	 */
	private String roomLogo;
	/**
	 * 床型
	 */
	private String bedType;
	/**
	 * 预定人
	 */
	private String name;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 1未付款,2已付款，3取消,4完成,5已入住,6申请退款,7退款,8拒绝退款
	 */
	private Integer status;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 折扣后的价格
	 */
	private BigDecimal disCost;
	/**
	 * 押金金额
	 */
	private BigDecimal yjCost;
	/**
	 * 优惠券价格
	 */
	private BigDecimal yhqCost;
	/**
	 * 会员折扣金额
	 */
	private BigDecimal yyzkCost;
	/**
	 * 总价格
	 */
	private BigDecimal totalCost;

	/**
	 * 总积分
	 */
	private BigDecimal totalIntegral;
	/**
	 * 是否删除,-1删除
	 */
	private Integer enabled;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 已退押金
	 */
	private BigDecimal retreatCost;
	/**
	 * 
	 */
	private BigDecimal hbCost;
	/**
	 * 
	 */
	private Integer hbId;
	/**
	 * 分类
	 */
	private Integer classify;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private Integer jjTime;
	/**
	 * 
	 */
	private Integer voice;
	/**
	 * 
	 */
	private String qrFromid;
	/**
	 * 订单快照
	 */
	private String orderInfo;

	/**
	 * 联系人ID
	 */
	private Long contactsId;

	/**
	 * 小程序模板消息使用
	 */
	private String formId;

	/**
	 * 支付方式
	 */
	private String payMethod;

	/**
	 * 评论标识
	 */
	private int commentFlag;
	
	/**
	 * 支付标识
	 */
	private int payFlag;

	/**
	 * 备注
	 */
	private String remark;
	
	@TableField(exist = false)
	private String	breakCouponName;
	
	@TableField(exist = false)
	private String freeRoomCouponName;

	@TableField(exist = false)
	private List<HotelOrderRecordEntity> records = new ArrayList<HotelOrderRecordEntity>();

}
